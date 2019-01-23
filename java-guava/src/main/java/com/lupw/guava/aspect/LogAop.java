package com.lupw.guava.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author v_pwlu 2019/1/22
 */
@Aspect
@Slf4j
@Component
public class LogAop {

    /**
     * 定义切点
     */
    @Pointcut(value = "@annotation(com.lupw.guava.aspect.AopAnnotation)")
    public void aopAnnotation(){
    }


    @Before(value = "aopAnnotation()")
    public void before(JoinPoint point) {
        // 使用 ProceedingJoinPoint 时 IDEA 提示只能在 @Around 注解的方法中被接收
        // 简单参数值
        Object[] args = point.getArgs();
        Arrays.stream(args).forEach(arg -> log.info("[LogAop] arg = {}", arg));

        // 代理类全类名, 获取到全类名, 就能获取 Class 的实例对象, 也就能使用反射了
        String className = point.getThis().getClass().getName();
        log.info("[LogAop] classType = {}", className);

        // 当前被调用方法的方法信息, 里面有参数名, 参数类型
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        String[] params = methodSignature.getParameterNames();
        log.info("[LogAop] methodName = {}", methodSignature.getMethod().getName());
        log.info("[LogAop] param0 = {}, param1 = {}", params[0], params[1]);
    }


    /**
     * 在 test 后运行
     * try {
     *     try{
     *         // @Before
     *         method.invoke(..);
     *     }finally{
     *         // @After
     *     }
     *     // @AfterReturning
     * } catch(){
     *     // @AfterThrowing
     * }
     */
    @After(value = "aopAnnotation()")
    public void after(JoinPoint point) {
        log.info("[LogAop] 1----------");
    }


    /**
     * 获取运行之后的返回值
     */
    @AfterReturning(returning = "o", pointcut = "aopAnnotation()")
    public Object afterReturning(JoinPoint point, Object o) {
        log.info("[LogAop] 2----------");
        return null;
    }


    @Around(value = "aopAnnotation()")
    public Object around(ProceedingJoinPoint point) {
        Object[] args = point.getArgs();
        try {
            // 获取返回方法返回值
            Object returnValue = point.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        // 对返回值进行修改后再返回新的值
        return null;
    }
}
