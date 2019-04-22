package com.lupw.guava.groovy;

import com.lupw.guava.datasource.db.mapper.UserMapper;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author v_pwlu 2019/4/19
 */
@Slf4j
@RestController
public class GroovyTestController {

    private final UserMapper db0UserMapper;
    private final ApplicationContext applicationContext;

    @Autowired
    public GroovyTestController(UserMapper db0UserMapper,
                                ApplicationContext applicationContext) {
        this.db0UserMapper = db0UserMapper;
        this.applicationContext = applicationContext;
    }

    @GetMapping(value = "/groovy/test")
    public void groovyTest() {
        BaseGroovySpot baseGroovySpot = applicationContext.getBean("groovyBean", BaseGroovySpot.class);
        log.info("content = {}", baseGroovySpot.test());
        log.info("content = {}", baseGroovySpot.test2());
    }


    /**
     * 可能出现的问题, 正在使用 groovyBean 时 groovyBean 这个实例被删除了导致出现异常
     */
    @GetMapping(value = "/groovy/add/bean")
    public void groovyAddBean() {
        String content = db0UserMapper.getGroovyContent("1");

        Class clazz = new GroovyClassLoader().parseClass(content);
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);

        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        autowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization(beanDefinition, "groovyBean");
        autowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(beanDefinition, "groovyBean");

        BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry) autowireCapableBeanFactory;
        if (beanRegistry.containsBeanDefinition("groovyBean")) {
            beanRegistry.removeBeanDefinition("groovyBean");
        }
        beanRegistry.registerBeanDefinition("groovyBean", beanDefinition);
    }
}
