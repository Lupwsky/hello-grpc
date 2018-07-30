package com.netflix.ribbon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/7/30
 */
@RestController
@Slf4j
public class CommonController {

    @GetMapping(value = "/ribbon/get/request/url")
    public String test(HttpServletRequest request) {
        log.warn("调用接口成功 : {}", request.getRequestURL().toString());
        return request.getRequestURI();
    }
}
