package com.cloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.netflix.client.ClientException;
import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.niws.client.http.RestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/7/27
 */
@RestController
@Slf4j
public class CommonController {

    private final RestTemplate restTemplate;

    @Autowired
    public CommonController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @PostMapping("/get/username")
    public void getUsername() {
        for (int i = 0; i< 10; i++) {
            getAndPrintUsername();
        }
    }


    private void getAndPrintUsername() {
        // 根据应用名称来调用
        String url = "http://cloud-provider0-dev/get/username";
        String username = restTemplate.getForObject(url, String.class);
        log.error("username = {}", username);
    }
}
