package com.lupw.guava;

import com.lupw.guava.datasource.MultiRoutingDataSourceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author v_pwlu 2019-01-11
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class GuavaApplication {

    public static void main(String[] args) {
        MultiRoutingDataSourceContext.setCurrentDataSourceBeanName("db0");
        SpringApplication.run(GuavaApplication.class, args);
    }

}

