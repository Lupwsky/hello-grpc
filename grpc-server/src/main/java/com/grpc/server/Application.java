package com.grpc.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

@SpringBootApplication
@Order(value = 3)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
