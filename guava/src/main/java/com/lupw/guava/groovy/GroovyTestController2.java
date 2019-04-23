package com.lupw.guava.groovy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author v_pwlu 2019/4/22
 */
@Slf4j
@RestController
public class GroovyTestController2 {

    private final ApplicationContext applicationContext;

    @Autowired
    public GroovyTestController2(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    /**
     * https://www.ibm.com/developerworks/cn/java/j-groovierspring2.html
     */
    @GetMapping(value = "/groovy/builder/test")
    public void groovyBuilderTest() {
        BaseGroovySpot baseGroovySpot = applicationContext.getBean("groovyBean", BaseGroovySpot.class);
        log.info("{}", baseGroovySpot.test());
    }
}
