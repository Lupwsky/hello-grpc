package com.grpc.client.service.web;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/7/19
 */
@Slf4j
@Component
public class RestTemplateServiceImpl {

    private final RestTemplate restTemplate;

    @Autowired
    public RestTemplateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void restTemplateGetRequest() {
        String url = "http://localhost:8001/grpc/demo/1/test";
        ResponseEntity<TestEntity> responseEntity = restTemplate.getForEntity(url, TestEntity.class);
        TestEntity body = responseEntity.getBody();
        log.warn("body = {}", body);
    }
}
