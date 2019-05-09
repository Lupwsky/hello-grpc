package com.lupw.guava.datasource.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lupw.guava.datasource.db.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author v_pwlu 2019/1/28
 */
@Repository(value = "db0UserMapper")
public interface UserMapper extends BaseMapper<UserInfo> {

    IPage<UserInfo> getUserInfoList(Page page, @Param("id") String id);

    int addUserInfo(@Param("name") String name, @Param("password") String password);

    String getGroovyContent(@Param("id") String id);
}
