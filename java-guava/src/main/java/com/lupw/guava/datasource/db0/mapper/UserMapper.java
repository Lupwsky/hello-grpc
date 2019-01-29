package com.lupw.guava.datasource.db0.mapper;

import org.springframework.stereotype.Repository;

/**
 * @author v_pwlu 2019/1/28
 */
@Repository(value = "db0UserMapper")
public interface UserMapper {
    String getUserName();
}
