package com.lupw.guava.hutool;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author v_pwlu 2019/5/23
 */
@Data
public class UserInfo implements Serializable {
    private String name;
    private String sex;
    private String age;
    private List<ClassInfo> classInfoList;
}
