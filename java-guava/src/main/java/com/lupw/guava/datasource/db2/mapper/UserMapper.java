package com.lupw.guava.datasource.db2.mapper;

import org.springframework.stereotype.Repository;

/**
 * @author v_pwlu 2019/1/28
 */
@Repository(value = "db2UserMapper")
public interface UserMapper {
    String getUserName();
}
