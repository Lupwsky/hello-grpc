package com.grpc.spring;

import io.grpc.ServerInterceptor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author cruzczhang
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface GRpcService {
    Class<? extends ServerInterceptor>[] interceptors() default {};
    boolean applyGlobalInterceptors() default true;
}
