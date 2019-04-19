package com.lupw.guava.datasource.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author v_pwlu 2019/1/28
 */
@Repository(value = "db0UserMapper")
public interface UserMapper {
    int addUserInfo(@Param("name") String name, @Param("password") String password);
}
