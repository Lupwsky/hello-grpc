package com.lupw.guava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author v_pwlu 2019-01-11
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableRetry
public class GuavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuavaApplication.class, args);
    }

}

