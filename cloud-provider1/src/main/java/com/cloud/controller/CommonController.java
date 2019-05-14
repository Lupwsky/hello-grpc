package com.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/7/27
 */
@RestController
public class CommonController {

    @GetMapping("/get/username")
    public String getUsername() {
        return "username-from-provider-1";
    }


    @GetMapping("/get/username/from/9021")
    public String getUsernameFrom9021() {
        return "username-from-9021";
    }
}
