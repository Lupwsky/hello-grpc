package com.grpc.proto.nospring;

import com.hello.grpc.proto.Request;
import com.hello.grpc.proto.Response;
import com.hello.grpc.proto.UserServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * Company: wesure
 * Project Name: hello-grpc
 * Description:
 *
 * @author v_pwlu
 * @date 2018/6/29
 */
public class TestServiceGrpcImpl extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void getUserInfo(Request request, StreamObserver<Response> responseObserver) {
        responseObserver.onNext(Response.newBuilder().setId(1).setName("Lupw").build());
        responseObserver.onCompleted();
    }
}
