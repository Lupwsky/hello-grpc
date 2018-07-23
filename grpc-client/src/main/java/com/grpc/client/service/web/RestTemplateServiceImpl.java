package com.grpc.client.service.web;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

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
        String url = "http://localhost:8001/grpc/demo/1/test?value={1}&value={2}";
        // expand 也接收 Map 值
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(url).build().expand("value1", "value2").encode();
        ResponseEntity<TestEntity> responseEntity = restTemplate.getForEntity(uriComponents.toUri(), TestEntity.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            TestEntity body = responseEntity.getBody();
            log.info("body = {}", body);
        } else {
            log.error("HttpStatus = {}", responseEntity.getStatusCode());
        }
    }


    public void restTemplatePostRequest() throws JsonProcessingException {
        String url = "http://localhost:8001/grpc/demo/3/test";

        // 设置为 JSON 提交
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // 可以使用 HashMap
        Map<String, Object> params= new HashMap<>();
        params.put("id", "1000");
        params.put("item", JSONObject.parse("{\"username\":\"lpw\", \"password\":\"123456\"}"));
        log.warn(JSONObject.toJSONString(params));

        // 构造 HttpEntity 请求参数, JSON 方式提交只能使用 JSON 字符串的方式
        HttpEntity<String> httpEntity = new HttpEntity<>(JSONObject.toJSONString(params), httpHeaders);

        // 发起请求
        ResponseEntity<TestEntity> responseEntity = restTemplate.postForEntity(url, httpEntity, TestEntity.class);
        TestEntity body = responseEntity.getBody();
        log.info("body = {}", body);
    }
}
