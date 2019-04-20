package com.lupw.guava.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author v_pwlu 2019/1/21
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class MultiProperties {
    private List<DataSourceProperties> crmProperties;
}
