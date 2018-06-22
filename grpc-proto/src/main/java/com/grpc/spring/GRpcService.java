package com.grpc.spring;

import io.grpc.ServerInterceptor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

// 注解的 Service 是 GRPC 服务的实现
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface GRpcService {
    Class<? extends ServerInterceptor>[] interceptors() default {};
    boolean applyGlobalInterceptors() default true;
}
