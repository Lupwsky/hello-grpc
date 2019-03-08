package com.location.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author v_pwlu 2019/3/7
 */
@Controller
public class RouterController {

    @GetMapping(value = "/error")
    public String error() {
        return "/error";
    }

    @GetMapping(value = "/location/templates/test")
    public String templatesTest() {
        return "hello";
    }

}
