package com.lupw.guava.hutool;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;

/**
 * @author v_pwlu 2019/5/23
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("LPW");
        userInfo.setAge("18");
        userInfo.setSex("男");
        userInfo.setClassInfoList(Arrays.asList(ClassInfo.builder().name("语文").score(100).build(),
                ClassInfo.builder().name("数学").score(80).build(),
                ClassInfo.builder().name("物理").score(90).build()));
        log.info("userInfo = {}", JSONObject.toJSONString(userInfo));

        // 不同类型对象的复制, 浅拷贝, 引用直接赋值
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfo, userInfoDTO);
        log.info("userInfoDTO = {}", JSONObject.toJSONString(userInfoDTO));

        // 深拷贝, 需要复制的类和类里面的引用实现 Serializable
        UserInfo userInfo1 = ObjectUtil.cloneByStream(userInfo);
        log.info("userInfo1 = {}", JSONObject.toJSONString(userInfo1));
    }
}
