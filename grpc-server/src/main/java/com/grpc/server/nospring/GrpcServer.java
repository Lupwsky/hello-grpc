package com.grpc.server.nospring;

import io.grpc.*;

import java.io.IOException;

/**
 * Company: wesure
 * Project Name: hello-grpc
 * Description:
 *
 * @author v_pwlu
 * @date 2018/6/21
 */
public class GrpcServer {
    private int port;
    private Server server;

    public GrpcServer(int port, BindableService bindableService, ServerInterceptor serverInterceptor) {
        this.port = port;
        start(bindableService, serverInterceptor);
    }


    /**
     * 创建并启动 gRPC 服务
     *
     * @param bindableService 服务接口实现
     */
    private void start(BindableService bindableService, ServerInterceptor serverInterceptor) {
        try {
            ServerServiceDefinition serverServiceDefinition =
                    ServerInterceptors.intercept(bindableService, serverInterceptor);
            // 可以添加多个服务接口实现
            server = ServerBuilder.forPort(port).addService(serverServiceDefinition).build().start();
            System.out.println("gRPC 服务已启动!");

            // JVM 停止运行的时候关闭 gRPC
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public synchronized void start() {
                    if (server != null) {
                        server.shutdown();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 停止 gRPC 服务
     *
     */
    private void stop() {
        if (server != null) {
            server.shutdown();
        }
        System.out.println("gRPC 服务已关闭!");
    }


    /**
     * 阻塞，一直到程序退出运行
     *
     * @throws InterruptedException
     */
    public void blockUntilShutdown() {
        if (server != null) {
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                stop();
            }
        }
    }
}
