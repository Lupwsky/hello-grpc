package com.lupw.guava.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lupw.guava.datasource.db.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author v_pwlu 2019/1/30
 */
@RestController
public class DbTestController {


    private final UserMapper userMapper;

    @Autowired
    public DbTestController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping(value = "/db/test")
    @Transactional(rollbackFor = Exception.class)
    public void dbTestController() {
        // 默认使用 db0
        userMapper.addUserInfo("lpw-db0", "123456");

        // 切换到数据源 db1
        MultiRoutingDataSourceContext.setCurrentDbKey("db1");
        userMapper.addUserInfo("lpw-db1", "123456");

        // 切换到数据源 db2
        MultiRoutingDataSourceContext.setCurrentDbKey("db2");
        userMapper.addUserInfo("lpw-db2", "123456");

        throw new RuntimeException();
    }


    @GetMapping(value = "/db/page/test/{num}/{size}")
    public void dbPageTest(@PathVariable("num") int num, @PathVariable("size") int size) {
        userMapper.getUserInfoList(new Page(num, size), "0");
    }
}
