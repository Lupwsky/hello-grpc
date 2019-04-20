package com.lupw.guava.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author v_pwlu 2019/1/28
 */
@Slf4j
public class ApplicationContextUtil implements ApplicationContextAware {

    public static ApplicationContext context;

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        if (context != null) {
            context = applicationContext;
        } else {
            log.info("applicationContext = null");
        }
    }
}
