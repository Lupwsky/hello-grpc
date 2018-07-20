package com.grpc.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CommonController {

    @RequestMapping(value = "/grpc/demo/1/test", method = RequestMethod.GET)
    public Map<String, Object> grpcTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 10000);
        map.put("message", "success");
        return map;
    }
}
