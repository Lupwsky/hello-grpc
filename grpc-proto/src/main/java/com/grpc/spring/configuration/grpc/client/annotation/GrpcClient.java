package com.grpc.spring.configuration.grpc.client.annotation;

import io.grpc.ClientInterceptor;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GrpcClient {
    // 注解到成员变量上，这个值表示需要连接的服务器的名称
    String value();

    // 注解到成员变量上，这个值表示客户端将要使用的拦截器
    Class<? extends ClientInterceptor>[] interceptors() default {};
}