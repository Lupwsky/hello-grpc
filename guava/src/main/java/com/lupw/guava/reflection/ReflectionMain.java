package com.lupw.guava.reflection;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * @author v_pwlu 2019/1/23
 */
@Slf4j
public class ReflectionMain {

    public static void main(String[] args) {

        try {
            // 获取 Class 对象实例
            // (1) Class.forName("全类名"), 代理类可以在获取到全类名后可以使用这种方法
            Class clazz = Class.forName(ReflectionClazz.CLASS_NAME);
            // (2) 知道具体类时, 类.class 和 Class 实例的 getComponentType() 方法均可获取到
            // Class clazz1 = ReflectionClazz.class;
            // (3) 有的类有一个静态属性 TYPE, 也可以获取到 Class 对象实例
            // Class clazz2 = Integer.TYPE;

            // 获取显示声明指定字段, private, public 的都会返回
            Field field = clazz.getDeclaredField("userInfo");
            // 成员名称
            log.info("[REFLECTION] name = {}", field.getName());

            // 成员类型
            log.info("[REFLECTION] type = {}", field.getType().getName());
            log.info("[REFLECTION] genericType = {}", field.getGenericType().getTypeName());
            log.info("[REFLECTION] annotatedType = {}", field.getAnnotatedType().getType());

            // 获取成员添加注解, 如果没有就返回 null, getAnnotations() 可以获取全部添加在上面的注解
            log.info("[REFLECTION] annotationValue = {}", field.getAnnotation(UserAnnotation.class).value());

            // 修饰符
            log.info("[REFLECTION] modifiersIndex = {}", field.getModifiers());
            log.info("[REFLECTION] modifiersValue = {}", Modifier.toString(field.getModifiers()));
            log.info("[REFLECTION] modifiersIsPrivate = {}", Modifier.isPrivate(field.getModifiers()));
            log.info("[REFLECTION] modifiersIsStatic = {}", Modifier.isPrivate(field.getModifiers()));


            // 返回当前类 (不包括父类) 声明所有字段, private 和 public 都会返回
            Field[] declaredFields = clazz.getDeclaredFields();
            Arrays.stream(declaredFields).forEach(declaredField -> {
                log.info("[REFLECTION] name = {}", declaredField.getName());
            });

            // 返回当前类和父类使用 public 声明的所有字段
            Field[] fields = clazz.getFields();
            Arrays.stream(fields).forEach(tempField -> {
                log.info("[REFLECTION] name = {}", tempField.getName());
            });

            // 获取方法和参数名
            Method method = clazz.getMethod("func1", String.class, String.class);
            Parameter[] parameter = method.getParameters();
            log.info("[REFLECTION] parameter0 = {}, parameter1 = {}", parameter[0].getName(), parameter[1].getName());

        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
