package com.grpc.server.controller;


import com.hello.grpc.proto.BooleanReply;
import com.hello.grpc.proto.ConditionsRequest;
import com.hello.grpc.proto.DeviceFix;
import com.hello.grpc.proto.DeviceFixServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

@GrpcService(DeviceFixServiceGrpc.class)
public class DeviceGrpcService extends DeviceFixServiceGrpc.DeviceFixServiceImplBase {

    @Override
    public void insertDeviceFix(DeviceFix request, StreamObserver<BooleanReply> responseObserver) {
        System.out.println("insertDeviceFix");

    }

    @Override
    public void updateDeviceFix(DeviceFix request, StreamObserver<BooleanReply> responseObserver) {
        System.out.println("updateDeviceFix");
    }

    @Override
    public void searchDeviceFix(ConditionsRequest request, StreamObserver<DeviceFix> responseObserver) {
        System.out.println("searchDeviceFix");
    }

    @Override
    public void deleteDeviceFix(ConditionsRequest request, StreamObserver<BooleanReply> responseObserver) {
        System.out.println("deleteDeviceFix");
    }
}
