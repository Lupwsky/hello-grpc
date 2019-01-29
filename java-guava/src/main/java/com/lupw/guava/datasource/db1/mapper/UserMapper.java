package com.lupw.guava.datasource.db1.mapper;

import org.springframework.stereotype.Repository;

/**
 * @author v_pwlu 2019/1/28
 */
@Repository(value = "db1UserMapper")
public interface UserMapper {
    String getUserName();
}
