package com.grpc.spring.client.auto.config.annotation;

import io.grpc.ClientInterceptor;

import java.lang.annotation.*;

/**
 * @author cruzczhang
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GrpcClient {

    String value();

    Class<? extends ClientInterceptor>[] interceptors() default {};
}