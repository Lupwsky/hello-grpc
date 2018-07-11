package com.grpc.client.service.grpc;

import com.grpc.client.configuration.grpc.GrpcClient;
import com.hello.grpc.proto.Request;
import com.hello.grpc.proto.Response;
import com.hello.grpc.proto.UserServiceGrpc;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Company: wesure
 * Project Name: hello-grpc
 * Description:
 *
 * @author v_pwlu
 * @date 2018/6/29
 */
@Slf4j
@Service
public class TestServiceGrpcImpl {

    @GrpcClient
    private Channel channel;

    public void getUserInfo() {
        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
        Response resp = stub.getUserInfo(Request.newBuilder().setId(1).build());
        log.info("name = {}, id = {]", resp.getName(), resp.getId());
    }
}
