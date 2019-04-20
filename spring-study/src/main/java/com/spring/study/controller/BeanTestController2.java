package com.spring.study.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.study.beans.MyAnnotation;
import com.spring.study.beans.UserInfo;
import com.spring.study.beans.UserInfoB;
import com.spring.study.beans.UserInfoC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;


/**
 * @author lupw 2019-03-30
 */
@Slf4j
@RestController
public class BeanTestController2 {

    private final Map<String, Object> beanMap;
    private final ApplicationContext applicationContext;

    @Autowired
    public BeanTestController2(Map<String, Object> beanMap,
                               ApplicationContext applicationContext) {
        this.beanMap = beanMap;
        this.applicationContext = applicationContext;
    }

    @GetMapping(value = "/bean/test2")
    public void test() {

    }
}
