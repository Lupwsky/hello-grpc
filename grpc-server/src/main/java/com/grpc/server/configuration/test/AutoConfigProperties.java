package com.grpc.server.configuration.test;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/7/12
 */
@Data
@PropertySource("classpath:application.properties")
@ConfigurationProperties("grpc.client")
public class AutoConfigProperties {
    private String host;
    private int port;
    private boolean plaintext;
    private boolean enableKeepAlive ;
    private long keepAliveDelay;
    private long keepAliveTimeout;
    private String serviceId;
}
