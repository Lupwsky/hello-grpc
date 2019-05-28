package com.example.javaagent;

import javassist.*;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;

/**
 * @author v_pwlu 2019/5/27
 */
@Slf4j
public class ClassFileTransformerTest implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!className.startsWith("com/example")) {
            return null;
        } else {
            log.info("className = {}", className);

            String fullClassName = className.replace("/", ".");
            log.info("fullClassName = {}", fullClassName);

            ClassPool classPool = ClassPool.getDefault();

            log.info("classPool = {}", "classPool");
            CtClass ctClass = null;
            try {
                ctClass = classPool.get(fullClassName);
            } catch (NotFoundException e) {
                // Spring 框架很有可能对我们的类进行扩展生成代理类, 这种情况下会出现找不到对应的 class 文件, 会出现 NotFoundException
                // 例如 com/example/javaagent/JavaagentApplication$$EnhancerBySpringCGLIB$$b02db345
                // com.example.javaagent.JavaagentApplication$$EnhancerBySpringCGLIB$$b02db345
                // 可以直接读取 classfileBuffer, 直接通过类的字节码创建CtClass对象, 或者简单点直接通过判断是否包含有 $ 符号直接判定
                try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(classfileBuffer)) {
                    ctClass = classPool.makeClass(byteArrayInputStream);
                } catch (IOException ex) {
                    log.error("exception = {}, error = {}", "IOException", ex);
                }
            }

            // 接口类不做处理
            if (ctClass == null || ctClass.isInterface()) {
                return null;
            }

            // 在原方法体前面和后面添加代码
            try {
                Object[] annotations = ctClass.getAnnotations();
                log.info("className = {}, annotations = {}", className, Arrays.toString(annotations));
                CtMethod[] methods = ctClass.getDeclaredMethods();
                for (CtMethod ctMethod : methods) {
                    log.info("className = {}, methodName = {}", className, ctMethod.getName());
                    ctMethod.insertBefore("System.out.println(\"" + className + " transform begin...\");");
                    ctMethod.insertAfter("System.out.println(\"" + className + " transform end...\");");
                }
                return ctClass.toBytecode();
            } catch (CannotCompileException | IOException e) {
                log.error("exception = {}, error = {}", "CannotCompileException", e);
                return null;
            } catch (ClassNotFoundException e) {
                log.warn("Slf4j 类不存在");
                return null;
            } finally {
                // 清除缓存
                ctClass.detach();
            }
        }
    }
}
