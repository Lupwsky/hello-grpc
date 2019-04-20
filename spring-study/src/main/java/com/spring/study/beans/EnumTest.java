package com.spring.study.beans;

import lombok.Getter;

/**
 * @author v_pwlu 2019/4/3
 */
public enum EnumTest {

    /**
     *
     */
    RED(0, "红色", "红色的描述"),

    /**
     *
     */
    WHITE(1, "白色", "白色的描述");

    @Getter
    private final int index;

    @Getter
    private final String name;

    @Getter
    private final String desc;

    EnumTest(int index, String name, String desc) {
        this.index = index;
        this.name = name;
        this.desc = desc;
    }

    public static EnumTest getInstance(String name) {
        switch (name) {
            case "白色": return WHITE;
            case "红色": return RED;
            default: throw new RuntimeException("错误的枚举类型");
        }
    }
}
