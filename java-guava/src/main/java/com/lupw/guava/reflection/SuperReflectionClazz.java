package com.lupw.guava.reflection;

/**
 * @author v_pwlu 2019/1/23
 */
public class SuperReflectionClazz {

    private UserInfo superUserInfo;


    private String superPrivateMember;


    private final String superPrivateFinalMember = "superPrivateFinalMemberValue";


    public String superPublicMember;


    public final String superPublicFinalMember = "superPublicFinalMemberValue";


    public String superFunc1(String num, String value) {
        return num + value;
    }


    private String superFunc2(String num, String value) {
        return num + value;
    }
}
