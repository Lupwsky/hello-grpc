package com.grpc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    // 测试回滚1
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
