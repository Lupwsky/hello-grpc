package com.grpc.server.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Repository
@Order(value = 2)
public class TestCommandLineRunner2 implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("测试2");
    }
}
