package com.grpc.client.configuration.grpc;

import io.grpc.ClientInterceptor;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GrpcClient {
}