package com.grpc.client.controller;

import com.hello.grpc.proto.Request;
import com.hello.grpc.proto.Response;
import com.hello.grpc.proto.TestServiceGrpc;
import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.stereotype.Service;


@Service
public class DeviceGrpcService {

    // 这里的名字和配置文件里的 grpc.client.[rpc-name].xxx 中的 rpc-name 是一致的
    @GrpcClient("device-grpc-server")
    private Channel serverChannel;


    public String insertDeviceFix() {
        TestServiceGrpc.TestServiceBlockingStub stub = TestServiceGrpc.newBlockingStub(serverChannel);
        Response response = stub.test(Request.newBuilder().setId(1).build());
        return "call success";
    }
}
