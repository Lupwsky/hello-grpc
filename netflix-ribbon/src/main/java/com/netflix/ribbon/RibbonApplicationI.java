package com.netflix.ribbon;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Scanner;

/**
 * @author v_pwlu
 */
@SpringBootApplication
public class RibbonApplicationI {

    public static void main(String[] args) {
        // 设置启动的服务器端口
        new SpringApplicationBuilder(RibbonApplicationI.class).properties( "server.port=" + 9041).run(args);
    }
}
