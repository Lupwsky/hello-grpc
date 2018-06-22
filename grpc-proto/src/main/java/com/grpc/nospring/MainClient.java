package com.grpc.nospring;

import com.alibaba.fastjson.JSON;
import com.hello.grpc.proto.Request;
import com.hello.grpc.proto.Response;
import com.hello.grpc.proto.TestServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Company: wesure
 * Project Name: hello-grpc
 * Description:
 *
 * @author v_pwlu
 * @date 2018/6/21
 */
public class MainClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 8000)
                .usePlaintext(true)
                .keepAliveWithoutCalls(true)
                .keepAliveTimeout(120, TimeUnit.SECONDS)
                .build();

        TestServiceGrpc.TestServiceBlockingStub blockingStub = TestServiceGrpc.newBlockingStub(channel);
        Response response = blockingStub.test(Request.newBuilder().setId(1).build());
        System.out.println("id = " + response.getId());
        System.out.println("name = " + response.getName());
    }
}
