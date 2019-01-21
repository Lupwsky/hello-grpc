package com.lupw.guava.joiner;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author v_pwlu 2019/1/18
 */
@Slf4j
public class JoinerTest {

    /**
     * JDK 7
     */
    public void joinerTest1() {
        // 转换成 "A,B,C,D,E"
        List<String> dataList = Lists.newArrayList("A", "B", "C", "D", "E");
        String value;

        // 在 String 没有提供内建的方法之前, 我们也许需要写如下一个循环来实现
        if (dataList != null) {
            StringBuilder stringBuilder = new StringBuilder(dataList.get(0));
            for (int i = 1; i < dataList.size(); i++) {
                stringBuilder.append(",").append(dataList.get(i));
            }
            value = stringBuilder.toString();
        } else {
            value = "";
        }
        log.info("[joinerTest1] value = {}", value);

        // 为了避免出现异常, 需要进一步进行判空处理
        // 这里为了方便使用 JDK 8 的 Optional 处理
        if (dataList != null) {
            StringBuilder stringBuilder = new StringBuilder(Optional.ofNullable(dataList.get(0)).orElse(""));
            for (int i = 1; i < dataList.size(); i++) {
                stringBuilder.append(",").append(Optional.ofNullable(dataList.get(i)).orElse(""));
            }
            value = stringBuilder.toString();
        } else {
            value = "";
        }
        log.info("[joinerTest1] value = {}", value);
    }



    /**
     * JDK 8
     */
    public void joinerTest2() {
        // 所幸的是 JDK 8 在 String 类里面提供了内建的 join 方法, 可以很方便的完成上面的功能
        // 里面使用 StringJoiner 类实现
        List<String> dataList = Lists.newArrayList("A", "B", "C", "D", "E");
        String value = String.join(",", dataList);
        log.info("[joinerTest2] value = {}", value);

        value = String.join(",", "A", "B", "C");
        log.info("[joinerTest2] value = {}", value);

        // 如果是拼接的是字符串是一个 null, 会将 "null", 字符串拼接
        // 以下例子输出的 value 值为 "A,B,C,null,null", 对于 null 需要进行特殊的额处理
        dataList = Lists.newArrayList("A", "B", "C", null, null);
        value = String.join(",", dataList);
        log.info("[joinerTest2] value = {}", value);

        value = String.join(",", "A", "B", "C", null, null);
        log.info("[joinerTest2] value = {}", value);
    }


    /**
     * Guava 提供的 Joiner 类可以更加便捷的处理
     */
    public void joinerTest3() {
        List<String> dataList = Lists.newArrayList("A", "B", null, null, "E");
        // skipNulls() 方法可以跳过所有 null 的数据
        String value = Joiner.on(",").skipNulls().join(dataList);
        log.info("[joinerTest3] value = {}", value);
        // 输出 A,B,E

        // useForNull() 方法替代 null
        value = Joiner.on(",").useForNull("NONE").join(dataList);
        log.info("[joinerTest3] value = {}", value);
        // 输出 A,B,NONE,NONE,E

        // Joiner 类还可以对 Map 里面的键值组合后进行拼接
        HashMap<String, String> hashMap = Maps.newHashMap();
        hashMap.put("K1", "1");
        hashMap.put("K2", "2");
        hashMap.put("K3", "3");
        value = Joiner.on(",").withKeyValueSeparator("/").join(hashMap);
        log.info("[joinerTest3] value = {}", value);
        // 输出 K1/1,K2/2,K3/3
    }


    /**
     * JDK 8 提供了一个新的类 StringJoiner, 也可以用于字符串使用分隔符拼接, 并且支持前后缀
     */
    public void joinerTest4() {
        // 单个参数分别为, 分隔符, 字符串凭借后添加的前缀, 字符串凭借后添加的后缀
        StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
        stringJoiner.add("A");
        stringJoiner.add("B");
        stringJoiner.add(null);
        stringJoiner.add("D");
        String value = stringJoiner.toString();
        log.info("[joinerTest4] value = {}", value);
        // 输出 [A,B,null,D]

        // 这个类被用于 String 内建的 join() 方法中和 Collectors.joining() 方法中
        // Collectors.joining() 的使用示例
        List<String> dataList = Lists.newArrayList("A", "B", null, null, "E");
        value = dataList.stream().filter(Objects::nonNull).collect(Collectors.joining(",", "[", "]"));
        log.info("[joinerTest4] value = {}", value);
    }
}
