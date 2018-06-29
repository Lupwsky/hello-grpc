package com.grpc.spring.client.auto.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grpc.spring.client.auto.config.annotation.GrpcClient;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import com.google.common.collect.Lists;

import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import lombok.SneakyThrows;

public class GrpcClientBeanPostProcessor implements org.springframework.beans.factory.config.BeanPostProcessor {

    private Map<String, List<Class>> beansToProcess = new HashMap<>();

    // 在 GrpcClientAutoConfiguration 的 Java 配置类中创建的 Bean，在这里注入
    @Autowired
    private DefaultListableBeanFactory beanFactory;

    // 在 GrpcClientAutoConfiguration 的 Java 配置类中创建的 Bean，在这里注入
    @Autowired
    private GrpcChannelFactory channelFactory;

    public GrpcClientBeanPostProcessor() { }


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
                        beansToProcess.put(beanName, new ArrayList<>());
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
                for (Field field : clazz.getDeclaredFields()) {
                    GrpcClient annotation = AnnotationUtils.getAnnotation(field, GrpcClient.class);
                    if (null != annotation) {
                        List<ClientInterceptor> list = Lists.newArrayList();
                        // clientInterceptorClass 是一个自定义的拦截器类，继承于 ClientInterceptor 类
                        for (Class<? extends ClientInterceptor> clientInterceptorClass : annotation.interceptors()) {
                            ClientInterceptor clientInterceptor;

                            // 获取所有类型为 ClientInterceptor.class 的 Bean
                            if (beanFactory.getBeanNamesForType(ClientInterceptor.class).length > 0) {
                                clientInterceptor = beanFactory.getBean(clientInterceptorClass);
                            } else { // 如果一个也没有，创建一个
                                try {
                                    clientInterceptor = clientInterceptorClass.newInstance();
                                } catch (InstantiationException | IllegalAccessException e) {
                                    throw new BeanCreationException("Failed to create interceptor instance", e);
                                }
                            }
                            // 拦截器添加到列表
                            list.add(clientInterceptor);
                        }
                        // 创建 channel，channel.value() 是客户端需要连接的服务器端名称
                        Channel channel = channelFactory.createChannel(annotation.value(), list);
                        // 暴力访问 field 字段
                        ReflectionUtils.makeAccessible(field);
                        // 设置 field 的值
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
