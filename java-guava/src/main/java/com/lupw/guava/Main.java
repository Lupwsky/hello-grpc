package com.lupw.guava;

import com.google.common.collect.ImmutableMultiset;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author v_pwlu 2019/1/11
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        // Guava 不可变集合
        ImmutableMultiset<String> immutableMultiset = ImmutableMultiset.of("1", "2", "2", "3", "4", "5", "6");
        immutableMultiset.forEach(log::info);

        // JDK 可变集合, 不会出现重复的元素
        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("2");
        set.add("2");
        set.add("3");
        set.add("4");
        set.add("5");

        // Collections.unmodifiableSet() 方法将原集合包装为不可变集合
        Set<String> unmodifiableSet = Collections.unmodifiableSet(set);
        unmodifiableSet.forEach(log::info);

        // JDK 不可变集合对原集合的引用进行修改, 仍然可以更改元素
        set.add("6");
        unmodifiableSet.forEach(log::info);

        // JDK 不可变集合虽然可以调用 add 方法, 但是会出现 UnsupportedOperationException 异常
        unmodifiableSet.add("7");
        unmodifiableSet.forEach(log::info);
    }
}
