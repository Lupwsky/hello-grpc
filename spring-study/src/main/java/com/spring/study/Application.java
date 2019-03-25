package com.spring.study;

import com.spring.study.context.DefineAnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author v_pwlu 2019/3/25
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setApplicationContextClass(DefineAnnotationConfigServletWebServerApplicationContext.class);
        springApplication.run(args);
    }
}
