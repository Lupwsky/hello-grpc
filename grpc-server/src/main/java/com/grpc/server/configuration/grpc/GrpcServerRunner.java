package com.grpc.server.configuration.grpc;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.type.StandardMethodMetadata;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Configuration
@EnableConfigurationProperties(value = {GrpcServerProperties.class})
public class GrpcServerRunner implements CommandLineRunner, DisposableBean  {

    private final AbstractApplicationContext applicationContext;

    private final GrpcServerProperties gRpcServerProperties;

    private Server server;


    @Autowired
    public GrpcServerRunner(AbstractApplicationContext applicationContext, GrpcServerProperties gRpcServerProperties) {
        this.applicationContext = applicationContext;
        this.gRpcServerProperties = gRpcServerProperties;
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("Starting gRPC Server ...");
        // 创建 ServerBuilder，绑定端口号，server 的 ip 使用默认的 0.0.0.0
        final ServerBuilder<?> serverBuilder = ServerBuilder.forPort(gRpcServerProperties.getPort());

        // 获取所有的类型为 BindableService 类的 bean 的名称，gRPC 的实现类均是 BindableService 的子类
        String[] beanNames = applicationContext.getBeanNamesForType(BindableService.class);
        for (String name : beanNames) {
            log.info(name);
        }

        // 获取所有使用了注解 @GrpcServer 的 bean 类
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(GrpcService.class);

        // 类型为 BindableService 且使用了 @GrpcServer 注解的 bean 类，才是一个有效的 gRPC 服务类
        // 因此继承实现了 BindableService 类但是没有使用 @GrpcServer 注解的类和使用了 @GrpcServer 注解的类但不是 BindableService 子类的 bean 都需要剔除掉
        List<String> beanNameList = new ArrayList<>();
        for (String name : beanNames) {
            if (beansWithAnnotation != null && beansWithAnnotation.containsKey(name)) {
                beanNameList.add(name);
            }
        }

        // 根据 beanName 获取这些 bean 实例，并绑定服务实现类
        for (String name : beanNameList) {
            BindableService bindableService = applicationContext.getBeanFactory().getBean(name, BindableService.class);
            serverBuilder.addService(bindableService);
            log.info("{} has been registered", name);
        }

//        // 获取所有使用 @GrpcServer 注解的 Bean，将其绑定
//        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(GrpcService.class);
//
//
//        getBeanNamesByTypeWithAnnotation(GrpcService.class, BindableService.class)
//                .forEach(name->{
//                    BindableService srv = applicationContext.getBeanFactory().getBean(name, BindableService.class);
//                    ServerServiceDefinition serviceDefinition = srv.bindService();
//                    GrpcService grpcServiceAnn = applicationContext.findAnnotationOnBean(name, GrpcService.class);
//                    serviceDefinition  = bindInterceptors(serviceDefinition,grpcServiceAnn, globalInterceptors);
//                    serverBuilder.addService(serviceDefinition);
//                    log.info("{} has been registered.", serviceDefinition.getServiceDescriptor().getName());
//
//                });

        // 启动服务类
        server = serverBuilder.build().start();
        log.info("gRPC Server started, listening on port {}", gRpcServerProperties.getPort());
        startDaemonAwaitThread();
    }


    @Override
    public void destroy() throws Exception {
        log.info("Shutting down gRPC server ...");
        Optional.ofNullable(server).ifPresent(Server::shutdown);
        log.info("gRPC server stopped.");
    }


    private ServerServiceDefinition bindInterceptors(ServerServiceDefinition serviceDefinition,
                                                     GrpcService gRpcService,
                                                     Collection<ServerInterceptor> globalInterceptors) {
        Stream<? extends ServerInterceptor> privateInterceptors = Stream.of(gRpcService.interceptors())
                .map(interceptorClass -> {
                    try {
                        return 0 < applicationContext.getBeanNamesForType(interceptorClass).length ?
                                applicationContext.getBean(interceptorClass) :
                                interceptorClass.newInstance();
                    } catch (Exception e) {
                        throw  new BeanCreationException("Failed to create interceptor instance.",e);
                    }
                });

        List<ServerInterceptor> interceptors = Stream.concat(
                    gRpcService.applyGlobalInterceptors() ? globalInterceptors.stream(): Stream.empty(),
                    privateInterceptors)
                .distinct()
                .collect(Collectors.toList());
        return ServerInterceptors.intercept(serviceDefinition, interceptors);
    }


    private void startDaemonAwaitThread() {
        Thread awaitThread = new Thread() {
            @Override
            public void run() {
                try {
                    GrpcServerRunner.this.server.awaitTermination();
                } catch (InterruptedException e) {
                    log.error("gRPC server stopped.",e);
                }
            }

        };
        awaitThread.setDaemon(false);
        awaitThread.start();
    }


    private <T> Stream<String> getBeanNamesByTypeWithAnnotation(Class<? extends Annotation> annotationType, Class<T> beanType) throws Exception{

       return Stream.of(applicationContext.getBeanNamesForType(beanType))
                .filter(name->{
                    final BeanDefinition beanDefinition = applicationContext.getBeanFactory().getBeanDefinition(name);
                    final Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(annotationType);

                    if ( !beansWithAnnotation.isEmpty() ) {
                        return beansWithAnnotation.containsKey(name);
                    } else if( beanDefinition.getSource() instanceof StandardMethodMetadata) {
                        StandardMethodMetadata metadata = (StandardMethodMetadata) beanDefinition.getSource();
                        return metadata.isAnnotated(annotationType.getName());
                    }
                    return false;
                });
    }
}
