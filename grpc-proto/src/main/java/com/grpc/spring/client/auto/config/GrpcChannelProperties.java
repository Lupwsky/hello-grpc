package com.grpc.spring.client.auto.config;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class GrpcChannelProperties {
    public static final String DEFAULT_HOST = "127.0.0.1";
    public static final Integer DEFAULT_PORT = 9090;

    private List<String> host = new ArrayList<>();
    private List<Integer> port = new ArrayList<>();
    private boolean plaintext = true;
    private boolean enableKeepAlive = false;
    private long keepAliveDelay = 60;
    private long keepAliveTimeout = 120;
    private String serviceId;
}
