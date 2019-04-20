package com.lupw.guava.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author v_pwlu 2019/1/28
 */
@Data
@Component(value = "db2DataSourceProperties")
@ConfigurationProperties(prefix = "spring.datasource.db2")
public class Db2DataSourceProperties {

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
