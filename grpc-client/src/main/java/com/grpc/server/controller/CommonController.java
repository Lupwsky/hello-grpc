package com.grpc.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CommonController {

    @Autowired
    private DeviceGrpcService deviceGrpcService;


    @RequestMapping(value = "/grpc/demo/2/test", method = RequestMethod.GET)
    public Map<String, Object> grpcTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 10000);
        map.put("msg", "success");
        String strReturn = deviceGrpcService.insertDeviceFix();
        System.out.print(strReturn);
        return map;
    }
}
