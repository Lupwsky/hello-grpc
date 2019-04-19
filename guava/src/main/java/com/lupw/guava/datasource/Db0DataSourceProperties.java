package com.lupw.guava.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author v_pwlu 2019/1/28
 *
 * 在 Component 里面指定 beanName, 默认的 beanName 是 db0DataSourceProperties
 */
@Data
@Component(value = "db0DataSourceProperties")
@ConfigurationProperties(prefix = "spring.datasource.db0")
public class Db0DataSourceProperties {

    private String name;

    private String driverClass;

    private String url;

    private String username;

    private String password;

    private int initialSize;

    private int minIdle;

    private int maxActive;

    private int maxWait;
}
