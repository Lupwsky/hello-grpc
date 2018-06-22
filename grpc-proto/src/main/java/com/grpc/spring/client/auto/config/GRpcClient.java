/**
 * @author careyzhu@tencent.com
 * @date 2017/6/22 15:38
 * @Copyright(c) WeSure Inc
 */
package com.grpc.spring.client.auto.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class GRpcClient {

    @Value("${grpc-client.port}")
    private int grpcClientPort;

    @Value("${grpc-client.ip}")
    private String grpcClientIp;

    //private final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6060).usePlaintext(true).build();
    private ThreadLocal channelContext = new ThreadLocal() {
        protected synchronized Object initialValue() {
            return ManagedChannelBuilder.forAddress(grpcClientIp, grpcClientPort).usePlaintext(true).build();
        }
    };

    public ManagedChannel getChannel() {
        return (ManagedChannel)channelContext.get();
    }

}
