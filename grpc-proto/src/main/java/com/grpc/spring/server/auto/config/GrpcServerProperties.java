package com.grpc.spring.server.auto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
// 设置配置文件， value 接收 String[] 类型的值，多个路径 value = {"classpath:xxx", "classpath:xxx"} 的格式设置
@PropertySource(value = "classpath:application.properties")
// 属性值匹配的前缀
@ConfigurationProperties(prefix="grpc.server")
@Data
public class GrpcServerProperties {
    private int port;
}
