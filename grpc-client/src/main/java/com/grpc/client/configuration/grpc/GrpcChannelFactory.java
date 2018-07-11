package com.grpc.client.configuration.grpc;

import io.grpc.Channel;
import io.grpc.ClientInterceptor;

import java.util.List;

/**
 * @author cruzczhang
 *
 */
public interface GrpcChannelFactory {

    Channel createChannel(String name);

    Channel createChannel(String name, List<ClientInterceptor> interceptors);
}
