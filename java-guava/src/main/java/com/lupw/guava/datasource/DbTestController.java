package com.lupw.guava.datasource;

import com.lupw.guava.datasource.db.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author v_pwlu 2019/1/30
 */
@RestController
public class DbTestController {


    private final UserMapper db0UserMapper;

    @Autowired
    public DbTestController(UserMapper db0UserMapper) {
        this.db0UserMapper = db0UserMapper;
    }

    @GetMapping(value = "/db/test")
    @Transactional(rollbackFor = Exception.class)
    public void dbTestController() {
        // 默认使用 db0
        db0UserMapper.addUserInfo("lpw-db0", "123456");

        // 切换到数据源 db1
        MultiRoutingDataSourceContext.setCurrentDbKey("db1");
        db0UserMapper.addUserInfo("lpw-db1", "123456");

        // 切换到数据源 db2
        MultiRoutingDataSourceContext.setCurrentDbKey("db2");
        db0UserMapper.addUserInfo("lpw-db2", "123456");

        throw new RuntimeException();
    }
}
