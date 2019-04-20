package com.lupw.guava.reflection;

import java.lang.annotation.*;

/**
 * @author v_pwlu 2019/1/23
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
public @interface UserAnnotation {
    String value() default "";
}
