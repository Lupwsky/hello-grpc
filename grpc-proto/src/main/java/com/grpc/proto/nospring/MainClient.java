package com.grpc.proto.nospring;

import com.hello.grpc.proto.Request;
import com.hello.grpc.proto.Response;
import com.hello.grpc.proto.UserServiceGrpc;
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
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 6080)
                .usePlaintext(true)
                .keepAliveWithoutCalls(true)
                .keepAliveTimeout(120, TimeUnit.SECONDS)
                .build();

        UserServiceGrpc.UserServiceBlockingStub blockingStub = UserServiceGrpc.newBlockingStub(channel);
        Response response = blockingStub.getUserInfo(Request.newBuilder().setId(1).build());
        System.out.println("id = " + response.getId());
        System.out.println("name = " + response.getName());
    }
}
