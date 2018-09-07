package com.grpc.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//        log.info("应用已经启动");


        String classpath = System.getProperty("java.class.path");
        log.info(classpath);

        classpath = System.getProperty("sun.boot.class.path");
        log.info(classpath);

        classpath = System.getProperty("java.ext.dirs");
        log.info(classpath);

        URL uri = Application.class.getClassLoader().getResource("");
        if (uri != null) {
            log.info(uri.getPath());
        } else {
            throw new RuntimeException("无法获取资源文件的URI");
        }

        Properties properties = new Properties();
        InputStream inputStream = Application.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(properties.getProperty("spring.application.name"));
    }
}
