package com.lupw.guava.groovy

import com.lupw.guava.datasource.db.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

/**
 * @author v_pwlu 2019/4/19
 */
@Component
class GroovyGroovySpot implements BaseGroovySpot {

    private final UserMapper db0UserMapper;

    @Autowired
    public GroovyGroovySpot(UserMapper db0UserMapper) {
        this.db0UserMapper = db0UserMapper;
    }

    public String test() {
        String result
        int i = db0UserMapper.addUserInfo("lpw", "test")
        if (i > 0) {
            result = "添加成功"
            println("添加成功")
        } else {
            result = "添加失败"
            println("添加失败")
        }
        return result
    }
}