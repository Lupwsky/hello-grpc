package com.grpc.spring.configuration.grpc.client;

import java.util.List;

import io.grpc.Channel;
import io.grpc.ClientInterceptor;

/**
 * @author cruzczhang
 *
 */
public interface GrpcChannelFactory {

    Channel createChannel(String name);

    Channel createChannel(String name, List<ClientInterceptor> interceptors);
}