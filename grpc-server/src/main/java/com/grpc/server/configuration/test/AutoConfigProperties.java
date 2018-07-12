package com.grpc.server.configuration.test;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/7/12
 */
@Data
@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties("grpc")
public class AutoConfigProperties {

    private Map<String, Configurations> client;

    @Data
    public static class Configurations {
        private List<String> host;

        private List<Integer> port;

        private long keepAliveDelay;

        private long keepAliveTimeout;

        private String serviceId;
    }
}
