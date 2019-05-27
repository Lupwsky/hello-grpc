package com.example.javaagent;

import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.Instrumentation;

/**
 * @author v_pwlu 2019/5/27
 */
@Slf4j
public class InstrumentationPremainTest {

    /**
     * 每一个类加载的时候都会回调此方法
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformerTest());
        log.info("agentArgs = {}", agentArgs);
    }
}
