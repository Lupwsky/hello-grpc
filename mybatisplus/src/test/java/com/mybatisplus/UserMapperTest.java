package com.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mybatisplus.db.mapper.UserMapper;
import com.mybatisplus.db.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author v_pwlu 2019/5/9
 */
@Slf4j
public class UserMapperTest extends MybatisplusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        List<UserInfo> userInfoList = userMapper.selectList(null);
        log.info("{}", userInfoList.toString());
    }
}
