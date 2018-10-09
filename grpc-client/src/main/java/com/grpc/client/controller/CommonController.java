package com.grpc.client.controller;

import com.grpc.client.Application;
import com.grpc.client.service.grpc.TestServiceGrpcImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@RestController
public class CommonController {

    private final TestServiceGrpcImpl testServiceGrpc;

    @Autowired
    public CommonController(TestServiceGrpcImpl testServiceGrpc) {
        this.testServiceGrpc = testServiceGrpc;
    }

    @RequestMapping(value = "/grpc/client/common/test", method = RequestMethod.GET)
    public Map<String, Object> grpcTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 10000);
        map.put("msg", "success");
        testServiceGrpc.getUserInfo();
        return map;
    }

    @RequestMapping(value = "/grpc/client/common/read/properties")
    public void testReadProperties() {
        Properties properties = new Properties();
        try {
            InputStream fileInputStream = new FileInputStream("D:\\IdeaProjects\\hello-grpc\\grpc-client\\src\\main\\resources\\application.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(properties.getProperty("spring.application.name"));
    }
}
