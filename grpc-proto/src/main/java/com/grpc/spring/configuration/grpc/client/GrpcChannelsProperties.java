package com.grpc.spring.configuration.grpc.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;


@Data
@ConfigurationProperties("grpc")
public class GrpcChannelsProperties {

    @NestedConfigurationProperty
    private Map<String, GrpcChannelProperties> client = new HashMap<>();

    public GrpcChannelProperties getChannel(String name) {
        return client.get(name);
    }
}
