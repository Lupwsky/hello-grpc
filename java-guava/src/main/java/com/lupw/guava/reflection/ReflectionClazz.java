package com.lupw.guava.reflection;

import lombok.Data;

/**
 * @author v_pwlu 2019/1/23
 */
public class ReflectionClazz extends SuperReflectionClazz {

    public static final String CLASS_NAME = "com.lupw.guava.reflection.ReflectionClazz";

    @UserAnnotation(value = "userAnnotationTest")
    private static UserInfo userInfo;


    private String privateMember;


    private final String privateFinalMember = "privateFinalMemberValue";


    public String publicMember;


    public final String publicFinalMember = "publicFinalMemberValue";


    public String func1(String num, String value) {
        return num + value;
    }


    private String func2(String num, String value) {
        return num + value;
    }
}
