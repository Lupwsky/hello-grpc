package com.grpc.client.service.grpc;

import com.grpc.client.configuration.grpc.GrpcClient;
import com.hello.grpc.proto.Request;
import com.hello.grpc.proto.TestServiceGrpc;
import io.grpc.Channel;

/**
 * Company: wesure
 * Project Name: hello-grpc
 * Description:
 *
 * @author v_pwlu
 * @date 2018/6/29
 */
public class TestServiceGrpcImpl {

    @GrpcClient("test-server-rpc")
    private Channel channel;

    public void test() {
        TestServiceGrpc.TestServiceBlockingStub stub = TestServiceGrpc.newBlockingStub(channel);
        stub.test(Request.newBuilder().setId(1).build());
    }
}
