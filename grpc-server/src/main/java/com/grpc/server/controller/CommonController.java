package com.grpc.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.grpc.server.domain.RequestInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class CommonController {

    @RequestMapping(value = "/grpc/demo/1/test", method = RequestMethod.GET)
    public Map<String, Object> grpcTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 10000);
        map.put("message", "success");
        return map;
    }


    @RequestMapping(value = "/grpc/demo/2/test", method = RequestMethod.POST)
    public Map<String, Object> postTest(String param1, String param2) {
        log.warn("param1 = {}, param2 = {}", param1, param2);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 10000);
        map.put("message", "success");
        return map;
    }


    @RequestMapping(value = "/grpc/demo/3/test", method = RequestMethod.POST)
    public Map<String, Object> postTest3(@RequestBody RequestInfo requestInfo) {
        log.warn("requestInfo = {}", requestInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 10000);
        map.put("message", "success");
        return map;
    }
}
