package com.grpc.spring;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author cruzczhang
 *
 */
@ConfigurationProperties(prefix="grpc-server")
@Data
public class GRpcServerProperties {
    /**
     * gRPC server port
     */
    private int port;

}
