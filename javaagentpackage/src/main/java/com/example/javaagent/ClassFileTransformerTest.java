package com.example.javaagent;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

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
                // Spring 和有可能通过字节码对我们的类进行扩展, 这种情况下找不到对应的class文件, 会报NotFoundException
                // 例如com/example/javaagent/JavaagentApplication$$EnhancerBySpringCGLIB$$b02db345
                // com.example.javaagent.JavaagentApplication$$EnhancerBySpringCGLIB$$b02db345
                // 直接读取classfileBuffer, 直接通过类的字节码创建CtClass对象
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
                CtMethod[] methods = ctClass.getDeclaredMethods();
                for (CtMethod ctMethod : methods) {
                    ctMethod.insertBefore("log.info(\"transform begin...\")");
                    ctMethod.insertAfter("log.info(\"transform end...\")");
                }
                return ctClass.toBytecode();
            } catch (CannotCompileException | IOException e) {
                log.error("exception = {}, error = {}", "CannotCompileException", e);
                return null;
            } finally {
                // 清除缓存
                ctClass.detach();
            }
        }
    }
}
