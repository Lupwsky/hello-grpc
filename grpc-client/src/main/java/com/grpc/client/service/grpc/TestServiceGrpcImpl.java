package com.grpc.client.service.grpc;

import com.grpc.client.configuration.grpc.GrpcClient;
import com.hello.grpc.proto.Request;
import com.hello.grpc.proto.UserServiceGrpc;
import io.grpc.Channel;
import org.springframework.stereotype.Service;

/**
 * Company: wesure
 * Project Name: hello-grpc
 * Description:
 *
 * @author v_pwlu
 * @date 2018/6/29
 */
@Service
public class TestServiceGrpcImpl {

    @GrpcClient("test-server-rpc")
    private Channel channel;

    public void getUserInfo() {
        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
        stub.getUserInfo(Request.newBuilder().setId(1).build());
    }
}
