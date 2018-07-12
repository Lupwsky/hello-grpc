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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

        Collection<ServerInterceptor> globalInterceptors = getBeanNamesByTypeWithAnnotation(GRpcGlobalInterceptor.class,ServerInterceptor.class)
                .map(name -> applicationContext.getBeanFactory().getBean(name,ServerInterceptor.class))
                .collect(Collectors.toList());

        final ServerBuilder<?> serverBuilder = ServerBuilder.forPort(gRpcServerProperties.getPort());

        // find and register all GRpcService-enabled beans
        getBeanNamesByTypeWithAnnotation(GrpcService.class,BindableService.class)
                .forEach(name->{
                    BindableService srv = applicationContext.getBeanFactory().getBean(name, BindableService.class);
                    ServerServiceDefinition serviceDefinition = srv.bindService();
                    GrpcService gRpcServiceAnn = applicationContext.findAnnotationOnBean(name,GrpcService.class);
                    serviceDefinition  = bindInterceptors(serviceDefinition,gRpcServiceAnn,globalInterceptors);
                    serverBuilder.addService(serviceDefinition);
                    log.info("{} has been registered.", serviceDefinition.getServiceDescriptor().getName());

                });

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
