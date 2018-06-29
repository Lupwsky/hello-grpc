package com.grpc.server.service.grpc;

import com.grpc.server.configuration.grpc.GrpcService;
import com.hello.grpc.proto.Request;
import com.hello.grpc.proto.Response;
import com.hello.grpc.proto.TestServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * Company: wesure
 * Project Name: hello-grpc
 * Description:
 *
 * @author v_pwlu
 * @date 2018/6/29
 */
@GrpcService
public class TestServiceGrpcImpl extends TestServiceGrpc.TestServiceImplBase {

    @Override
    public void test(Request request, StreamObserver<Response> responseObserver) {
        responseObserver.onNext(Response.newBuilder().setId(1).setName("Lupw").build());
        responseObserver.onCompleted();
    }
}
