package com.thread.excutor.beancopy;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;


/**
 * @author v_pwlu 2018/12/13
 */
@Slf4j
public class Main {
    private static final BeanCopier BEAN_COPIER = BeanCopier.create(User1.class, User2.class, true);
    
    public static void main(String[] args) {
        copyTest1();
    }


    /**
     * 只要属性类型, 名称都相同, 复杂的对象和集合都可以全部拷贝
     */
    private static void copyTest1() {
        User1 user1 = User1.builder().name1("LU").detailInfo1(DetailInfo1.builder().age(10).sex("女").build()).build();
        User2 user2 = User2.builder().build();
        BEAN_COPIER.copy(user1, user2, new MConverter());
        log.info(JSONObject.toJSONString(user1));
        log.info(JSONObject.toJSONString(user2));
    }

    public static class MConverter implements Converter {
        @Override
        public Object convert(Object o, Class aClass, Object o1) {
            System.out.println(o.getClass() + " " + o);
            System.out.println(aClass);
            System.out.println(o1.getClass() + " " + o1);
            return o;
        }
    }
}
