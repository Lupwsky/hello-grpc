package com.lupw.guava.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author v_pwlu 2019/1/21
 */
@Slf4j
@RestController
public class AopTestController {

    @PostMapping(value = "/aop/test")
    @AopAnnotation
    public void test(String name, String userId) {
        log.info("[aopTest] name = {}, userId = {}", name, userId);
    }
}
