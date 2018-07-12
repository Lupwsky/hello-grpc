package com.grpc.server.nospring;


import com.grpc.server.service.grpc.TestServiceGrpcImpl;

/**
 * Company: wesure
 * Project Name: hello-grpc
 * Description:
 *
 * @author v_pwlu
 * @date 2018/6/21
 */
public class MainServer {
    public static void main(String[] args) {
        TestServiceGrpcImpl testServiceGrpc = new TestServiceGrpcImpl();
        GrpcServer server = new GrpcServer(6080, testServiceGrpc, null);
        server.blockUntilShutdown();
        System.out.println("进程结束!");
    }
}
