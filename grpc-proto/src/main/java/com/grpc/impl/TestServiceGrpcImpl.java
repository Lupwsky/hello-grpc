package com.grpc.impl;


import com.grpc.spring.server.auto.config.annotation.GrpcService;
import com.hello.grpc.proto.Request;
import com.hello.grpc.proto.Response;
import com.hello.grpc.proto.TestServiceGrpc;
import io.grpc.stub.StreamObserver;

@GrpcService
public class TestServiceGrpcImpl extends TestServiceGrpc.TestServiceImplBase {

    @Override
    public void test(Request request, StreamObserver<Response> responseObserver) {
        int id = request.getId();
        responseObserver.onNext(Response.newBuilder().setId(id).setName("test").build());
        responseObserver.onCompleted();
    }
}
