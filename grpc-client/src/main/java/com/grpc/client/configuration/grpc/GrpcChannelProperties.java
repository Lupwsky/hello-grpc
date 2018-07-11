package com.grpc.client.configuration.grpc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Data
@Configuration
@ConfigurationProperties("grpc.client")
public class GrpcChannelProperties {
    private String host;
    private int port;
}
