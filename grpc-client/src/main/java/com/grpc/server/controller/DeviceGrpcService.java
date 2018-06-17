package com.grpc.server.controller;

import com.hello.grpc.proto.BooleanReply;
import com.hello.grpc.proto.DeviceFixServiceGrpc;
import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.stereotype.Service;


@Service
public class DeviceGrpcService {

    @GrpcClient("device-grpc-server")
    private Channel serverChannel;


    public String insertDeviceFix() {
        DeviceFixServiceGrpc.DeviceFixServiceBlockingStub stub = DeviceFixServiceGrpc.newBlockingStub(serverChannel);
        BooleanReply response = stub.insertDeviceFix(null);
        return "call success";
    }
}
