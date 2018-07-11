package com.grpc.client.configuration.grpc;

import com.google.common.collect.Lists;
import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannelBuilder;
import lombok.SneakyThrows;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@EnableConfigurationProperties(value = {GrpcChannelProperties.class})
public class GrpcClientBeanPostProcessor implements org.springframework.beans.factory.config.BeanPostProcessor {

    private Map<String, List<Class>> beansToProcess = new HashMap<>();

    @Autowired
    private GrpcChannelProperties channelProperties;

    // 在 Bean 初始化前调用，每个 Bean 创建前都会调用
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();
        do {
            // 遍历这个 Bean 类的所有字段，如果这个 Bean 里面有字段使用了注解 @GrpcClient
            // 添加到以 beanName 为 key 值的 Map 中
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(GrpcClient.class)) {
                    if (!beansToProcess.containsKey(beanName)) {
                        beansToProcess.put(beanName, new ArrayList<Class>());
                    }
                    beansToProcess.get(beanName).add(clazz);
                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return bean;
    }


    // 在 Bean 创建完成后调用
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beansToProcess.containsKey(beanName)) {
            Object target = getTargetBean(bean);
            for (Class clazz : beansToProcess.get(beanName)) {
                // 循环遍历类的字段，找到使用了 @GrpcClient 注解的字段
                for (Field field : clazz.getDeclaredFields()) {
                    GrpcClient annotation = AnnotationUtils.getAnnotation(field, GrpcClient.class);
                    if (null != annotation) {
                        // 创建 channel，channel.value() 是客户端需要连接的服务器端名称
                        Channel channel = ManagedChannelBuilder.forAddress(channelProperties.getHost(), channelProperties.getPort())
                                .usePlaintext(true)
                                .keepAliveWithoutCalls(true)
                                .keepAliveTimeout(120, TimeUnit.SECONDS)
                                .build();
                        // 暴力访问 field 字段
                        ReflectionUtils.makeAccessible(field);
                        // 设置 field 的值，将 channel 的值设置和这个字段
                        ReflectionUtils.setField(field, target, channel);
                    }
                }
            }
        }
        return bean;
    }

    @SneakyThrows
    private Object getTargetBean(Object bean) {
        Object target = bean;
        while (AopUtils.isAopProxy(target)) {
            target = ((Advised) target).getTargetSource().getTarget();
        }
        return target;
    }
}
