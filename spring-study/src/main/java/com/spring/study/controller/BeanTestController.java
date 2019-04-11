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
public class BeanTestController {

    private final Map<String, Object> beanMap;
    private final ApplicationContext applicationContext;

    @Autowired
    public BeanTestController(Map<String, Object> beanMap,
                              ApplicationContext applicationContext) {
        this.beanMap = beanMap;
        this.applicationContext = applicationContext;
    }

    @GetMapping(value = "/bean/test/create")
    public void testCreate() {
        register("userInfoC1", UserInfoC.class);
        register("userInfoC2", UserInfoC.class);
    }

    @GetMapping(value = "/bean/test")
    public void test() {
        log.info("{}", beanMap.size());
        log.info("{}", applicationContext.containsBeanDefinition("userInfoA"));
        log.info("{}", applicationContext.getBeanDefinitionCount());
        log.info("{}", Arrays.toString(applicationContext.getBeanDefinitionNames()));

        ResolvableType resolvableType = ResolvableType.forClass(UserInfo.class);
        log.info("{}", Arrays.toString(applicationContext.getBeanNamesForType(resolvableType)));
        log.info("{}", Arrays.toString(applicationContext.getBeanNamesForType(UserInfoB.class, false, false)));

        Map<String, UserInfoC> userInfoMap = applicationContext.getBeansOfType(UserInfoC.class);
        UserInfoC userInfo = userInfoMap.get("userInfoC1");
        userInfo.setUserInfoC("lpw");
        log.info("{}", JSONObject.toJSONString(userInfo));


        Map<String, Object> map = applicationContext.getBeansWithAnnotation(MyAnnotation.class);
        UserInfo info = (UserInfo) map.get("userInfo");
        info.setName("lpw");
        info.setName("lpw@qq.com");
        log.info("{}", JSONObject.toJSONString(info));

        MyAnnotation myAnnotation = applicationContext.findAnnotationOnBean("userInfo", MyAnnotation.class);
        log.info("{}", myAnnotation.value());
    }

    private void register(String beanName,Class<?> beanClass){
        BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
        try {
            beanRegistry.getBeanDefinition(beanName);
            log.info("容器中已经注册了指定名称的 bean");
        } catch (Exception e) {
            GenericBeanDefinition definition =  new GenericBeanDefinition();
            definition.setBeanClass(beanClass);
            definition.setScope(BeanDefinition.SCOPE_SINGLETON);
            beanRegistry.registerBeanDefinition(beanName, definition);
        }
    }

}
