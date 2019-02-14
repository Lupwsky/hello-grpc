package com.lupw.guava.properties;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author v_pwlu 2019/1/21
 */
@Slf4j
@RestController
@EnableConfigurationProperties(value = MultiProperties.class)
public class TestController {
    private final MultiProperties multiProperties;


    @Autowired
    public TestController(MultiProperties multiProperties) {
        this.multiProperties = multiProperties;
    }


    @GetMapping(value = "/properties/test")
    public void test() {
        log.info("{}", JSONObject.toJSONString(multiProperties.getCrmProperties()));
        log.info("[--]");
    }
}
