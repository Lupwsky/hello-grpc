package com.lupw.guava.splitter;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author v_pwlu 2019/1/18
 */
@Slf4j
public class SplitterTest {

    /**
     * JDK 中 String 的方法 split()
     */
    public void splitterTest1() {
        String value, sourceData = "A,B,C";
        value = Arrays.toString(sourceData.split(","));
        log.info("[splitterTest1] value = {}", value);
        // 输出 [A, B, C]

        // String.split() 方法默认情况下会丢弃最后末尾的分隔符
        sourceData = "A,B,C,";
        value = Arrays.toString(sourceData.split(","));
        log.info("[splitterTest1] value = {}", value);
        // 输出 [A, B, C]

        // 如果不想丢弃最后末尾的分隔符, 使用重载后的方法, 借助第二个参数来实现, 使用示例如下
        sourceData = "A,B,C,,,";
        value = Arrays.toString(sourceData.split(",", -1));
        log.info("[splitterTest1] value = {}", value);
        // 输出 [A, B, C, , ,]
    }


    /**
     * Guava 提供的 Splitter 类
     */
    public void splitterTest2() {
        // Splitter 的使用示例
        // Splitter.fixedLength(int) 按固定长度拆分, 最后一段可能比给定长度短, 但不会为空字符串
        String value, sourceData = "A,B,C,,";

        // omitEmptyStrings() 自动忽略空字符串
        value = Lists.newArrayList(Splitter.on(",").omitEmptyStrings().split(sourceData)).toString();
        log.info("[splitterTest2] value = {}", value);
        // 输出 [A, B, C]

        // trimResults() 移除每个分隔出来的字符串的前后空格
        sourceData = "A, B , C,,";
        value = Lists.newArrayList(Splitter.on(",").omitEmptyStrings().split(sourceData)).toString();
        log.info("[splitterTest2] value = {}", value);

        value = Lists.newArrayList(Splitter.on(",").omitEmptyStrings().trimResults().split(sourceData)).toString();
        log.info("[splitterTest2] value = {}", value);
        // 输出 [A,  B ,  C]
        // 输出 [A, B, C]

        // trimResults(CharMatcher) 给给你个匹配其, 移除每个分隔出来的字符串的前后匹配的字符
        sourceData = "A, B你好, 你好C,,";
        value = Lists.newArrayList(Splitter.on(",")
                .trimResults(CharMatcher.anyOf("你好"))
                .omitEmptyStrings()
                .split(sourceData))
                .toString();
        log.info("[splitterTest2] value = {}", value);
        // 输出 [A,  B,  你好C]
        // 这里的你好没有移除是因为, 分隔出来的是 " 你好C", 前面多一个空格
    }
}
