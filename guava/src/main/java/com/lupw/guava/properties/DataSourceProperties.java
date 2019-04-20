package com.lupw.guava.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author v_pwlu 2019/1/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceProperties {
    private String url;
    private String username;
}
