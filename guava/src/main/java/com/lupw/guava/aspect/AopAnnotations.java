package com.lupw.guava.aspect;

import java.lang.annotation.*;

/**
 * @author v_pwlu 2019/1/24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopAnnotations {
    AopAnnotation[] value();
}
