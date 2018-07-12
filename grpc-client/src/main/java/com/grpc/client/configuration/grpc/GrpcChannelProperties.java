package com.grpc.client.configuration.grpc;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class GrpcChannelProperties {
    public final static String DEFAULT_HOST = "127.0.0.1";
    public final static Integer DEFAULT_PORT = 6000;

    private List<String> host = new ArrayList<>();
    private List<Integer> port = new ArrayList<>();
    private boolean plaintext = true;
    private boolean enableKeepAlive = false;
    private long keepAliveDelay = 60;
    private long keepAliveTimeout = 120;
    private String serviceId;
}
