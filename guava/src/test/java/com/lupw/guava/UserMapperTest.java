package com.lupw.guava;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lupw.guava.datasource.db.mapper.UserMapper;
import com.lupw.guava.datasource.db.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author v_pwlu 2019/5/9
 */
@Slf4j
public class UserMapperTest extends GuavaApplicationTests {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void userMapperTest() {
        // 调用 XML 中的 SQL 添加数据
        // userMapper.addUserInfo("lpw-db0", "123456");

        // 查询全部
        List<UserInfo> userInfoList = userMapper.selectList(null);
        log.info("{}", userInfoList.toString());


        // new QueryWrapper<UserInfo>().ge("id", 2).le("id", 5);
        List<UserInfo> userInfoList1 = userMapper.selectList(new QueryWrapper<UserInfo>().lambda()
                .ge(UserInfo::getId, 2)
                .le(UserInfo::getId, 5));
        log.info("{}", userInfoList1.toString());

        // 分页
        int pageNum = 1, pageSize = 2;
        IPage<UserInfo> userInfoIPage = userMapper.selectPage(new Page<>(pageNum, pageSize), null);
        log.info("{}", JSONObject.toJSONString(userInfoIPage));
        userInfoIPage.getTotal();
        userInfoIPage.getCurrent();
        userInfoIPage.getPages();
        userInfoIPage.getSize();
        List<UserInfo> userInfoList2 = userInfoIPage.getRecords();

        // 分页, XML
        IPage<UserInfo> userInfoIPage1 = userMapper.getUserInfoList(new Page<>(pageNum, pageSize), "0");
        log.info("{}", JSONObject.toJSONString(userInfoIPage1));
        userInfoIPage.getTotal();  // 总数
        userInfoIPage.getCurrent(); // 当前页码
        userInfoIPage.getPages(); // 总页数
        userInfoIPage.getSize();  //  每页数量
        List<UserInfo> userInfoList3 = userInfoIPage.getRecords();
    }
}
