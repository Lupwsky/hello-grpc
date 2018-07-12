package com.grpc.client.controller;

import com.grpc.client.service.grpc.TestServiceGrpcImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CommonController {

//    private final TestServiceGrpcImpl testServiceGrpc;
//
//    @Autowired
//    public CommonController(TestServiceGrpcImpl testServiceGrpc) {
//        this.testServiceGrpc = testServiceGrpc;
//    }

    @RequestMapping(value = "/grpc/client/common/test", method = RequestMethod.GET)
    public Map<String, Object> grpcTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 10000);
        map.put("msg", "success");
//        testServiceGrpc.getUserInfo();
        return map;
    }
}
