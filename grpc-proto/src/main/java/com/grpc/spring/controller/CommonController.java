package com.grpc.spring.controller;

import com.grpc.spring.service.web.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Company: wesure
 * Project Name: hello-grpc
 * Description:
 *
 * @author v_pwlu
 * @date 2018/6/29
 */
@RestController
public class CommonController {
    private final TestServiceImpl service;


    @Autowired
    public CommonController(TestServiceImpl service) {
        this.service = service;
    }


    @RequestMapping(value = "/common/test", method = RequestMethod.GET)
    public void testServiceTest() {
        service.testServiceTest();
    }
}
