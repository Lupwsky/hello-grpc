package com.spring.study.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lupw 2019-03-30
 */
@Slf4j
@Component
public class BeanTest {


    private final BeanFactory beanFactory;
    private final ApplicationContext applicationContext;

    @Autowired
    public BeanTest(BeanFactory beanFactory, ApplicationContext applicationContext) {
        this.beanFactory = beanFactory;
        this.applicationContext = applicationContext;
    }

    public void test() {
        BeanTest beanTest = beanFactory.getBean("beanTest", BeanTest.class);
        beanTest.print();
    }

    public void print() {
        log.info("test");

        List<String> aList = Lists.newArrayList();
        List<String> bList = Lists.newArrayList();
        Stream.concat(aList.stream(), bList.stream()).distinct().collect(Collectors.toList());
    }
}
