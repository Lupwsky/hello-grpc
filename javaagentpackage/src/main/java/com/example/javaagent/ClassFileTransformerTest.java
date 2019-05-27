package com.example.javaagent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

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
        if (className.startsWith("com/example")) {
            log.info("className = {}", className);
            ClassPool classPool = ClassPool.getDefault();
            try {
                CtClass ctClass = classPool.getCtClass(className.replace("/", "\\."));
                log.info("ctClass = {}", ctClass);
            } catch (NotFoundException e) {
                log.error("exception = {}, error = {}", "NotFoundException", e);
            }
        }
        return new byte[0];
    }
}
