package com.grpc.spring;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

// 注解在的拦截器的类型注意是 ServerInterceptor
@Target({ElementType.TYPE,ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface GRpcGlobalInterceptor {
}
