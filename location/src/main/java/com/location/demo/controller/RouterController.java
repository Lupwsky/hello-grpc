package com.location.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author v_pwlu 2019/3/7
 */
@Controller
public class RouterController {

    @GetMapping(value = "/error")
    public String error() {
        return "/error";
    }

    @RequestMapping(value = "/location/templates/test")
    public ModelAndView templatesTest() {
        ModelAndView modelAndView = new ModelAndView("hello");
        return modelAndView;
    }

}
