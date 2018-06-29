package com.grpc.server.configuration.grpc;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author cruzczhang
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface GRpcGlobalInterceptor {
}
