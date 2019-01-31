package com.lupw.guava.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @author v_pwlu 2019/1/28
 */
@Slf4j
public class MultiRoutingDataSourceContext {

    private final static ThreadLocal<String> DB_KEY = new ThreadLocal<>();

    private MultiRoutingDataSourceContext() {
    }


    public static String getCurrentDbKey() {
        return DB_KEY.get();
    }


    public static void setCurrentDbKey(String tenant) {
        DB_KEY.set(tenant);
    }


    public static void clear() {
        DB_KEY.remove();
    }
}
