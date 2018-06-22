package com.grpc.server.grpc.impl;


import com.hello.grpc.proto.Request;
import com.hello.grpc.proto.Response;
import com.hello.grpc.proto.TestServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

@GrpcService(TestServiceGrpc.class)
public class DeviceGrpcService extends TestServiceGrpc.TestServiceImplBase {

    // 实现 gRPC 的 test 方法
    @Override
    public void test(Request request, StreamObserver<Response> responseObserver) {
        int id = request.getId();
        responseObserver.onNext(Response.newBuilder().setId(id).setName("test").build());
        responseObserver.onCompleted();
    }
}
