package com.spring.study.groovy;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author v_pwlu 2019/4/18
 */
@Slf4j
public class GroovyMain {

    public static void main(String[] args) {
        // GroovyScriptEngine的根路径，如果参数是字符串数组，说明有多个根路径
        GroovyScriptEngine engine = null;
        try {
            engine = new GroovyScriptEngine("D:\\Work\\hello-grpc\\spring-study\\src\\main\\java\\com\\spring\\study\\groovy");
            Binding binding = new Binding();
            binding.setVariable("name", "lpw");

            Object result1 = engine.run("hello.groovy", binding);
            System.out.println(result1);
        } catch (IOException | ScriptException | ResourceException e) {
            e.printStackTrace();
        }
    }

}
