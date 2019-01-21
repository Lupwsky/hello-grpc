package com.lupw.guava.set;


import com.google.common.collect.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author v_pwlu
 */
@Slf4j
public class GuavaSetMain {

    public static void main(String[] args) {
        // Guava 添加了一些新的集合类型
        multimapTest();
    }


    private static void multiSetTest() {
        // MultiSet 是 Set 的延伸, 但是和 set 不同的是他的元素可以重复出现, 并且 MultiSet 是继承 Collection 实现的
        // 以下例子会输出 1, 2, 3
        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("2");
        set.add("2");
        set.add("3");
        set.forEach(log::info);

        // 以下例子会输出 1, 2, 2, 3
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("1");
        multiset.add("2");
        multiset.add("2");
        multiset.add("3");
        multiset.forEach(log::info);

        // MultiSet 可以很方便的对键值进行计数, 如果使用
        // 这个例子计算键 1 出现的次数, 加上之前放入的 1 次, 总共次数为三次
        multiset.add("1", 2);  // key 为 1 的键加两次计数, 可以理解为是两个 add("1")
        log.info(String.valueOf(multiset.count("1")));

        // 因此将 MultiSet 看做一个普通的 Collection 的时候, 是一个无序的 ArrayList,
        // 当看成 Map<E, Integer> 类型时, 则表示键为元素, 值为计数的 Map 类型
        // MultiSet 既是 Collection 也是 Map
        // MultiSet 的其他方法
        // iterator() 返回一个迭代器, 包含 Multiset 的所有元素
        // size() 返回包括重复的元素所有元素的总计数数量,
        // count() 返回给定元素的计数
        // elementSet() 返回所有不重复元素的 Set, elementSet().size() 可以获取到不重复的元素数量
        // entrySet() 返回包括重复元素的 Set<Multiset.Entry>
        // remove(E e, int count) 减少给定元素的计数
        // setCount(E e, int count) 设置给定元素的计数, 不允许为负数, 为 0 时表示删除元素
        // setCount(E e, int oldCount, int newCount) 设置给定元素的计数, 如果 oldCount 的值和目前计数相同, 则设置成新值, 否则不变
        multiset.setCount("1", 2, 3);
        log.info(String.valueOf(multiset.count("1")));
        multiset.setCount("1", 3, 2);
        log.info(String.valueOf(multiset.count("1")));

        // 在 JDK 7 之前Map 实现计数
        Map<String, Integer> countsMap = new HashMap<>();
        if (countsMap.containsKey("1")) {
            int count = countsMap.get("1");
            countsMap.put("1", count + 1);
        } else {
            countsMap.put("1", 0);
        }

        // 将 MultiSet 用作 Map 时, 对应 JDK 各个 Map 的实现如下
        // HashMap -> HashMultiset
        // TreeMap -> TreeMultiset
        // LinkedHashMap -> LinkedHashMultiset
        // LinkedHashMultiset -> ConcurrentHashMultiset
        // ImmutableMap -> ImmutableMultiset
    }


    private static void multimapTest() {
        // 想 Map<String, List<Object>> ObjectMap = new HashMap<>>() 这样的数据结构,
        // 在使用的时候需要检查 key 是否存在, 不存在时则创建一个, 存在时在List后面添加上一个, 这样的过程是比较繁琐的
        // 使用实例, 略, 后面补上

        // Multimap 可以很方便的解决这个问题 (常用的有 ListMultimap 和 SetMultimap)
        // ArrayListMultimap<String, String> -> Map<String, List<String>>
        ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
        // 如果 对应键值的 List 不存在, put 的时候会自动创建, 免去我们还要去检测的步骤
        multimap.put("1", "1");
        List<String> dataList1 = multimap.get("1");
        dataList1.forEach(log::info);

        // 如果获取的 key 对应的 List 不存在, 会返回一个空的集合
        List<String> dataList2 = multimap.get("2");
        log.info("{}", dataList2.size());

        // 其他的
        // ArrayListMultimap
        // HashMultimap
        // LinkedListMultimap
        // LinkedHashMultimap
        // TreeMultimap
        // ImmutableListMultimap
        // ImmutableSetMultimap
    }


    public static void immutableMulitSetTest() {
        // Guava 不可变集合
        ImmutableMultiset<String> immutableMultiset = ImmutableMultiset.of("1", "2", "2", "3", "4");
        immutableMultiset.forEach(log::info);

        // JDK 可变集合, 不会出现重复的元素
        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("2");
        set.add("2");
        set.add("3");
        set.add("4");

        // Collections.unmodifiableSet() 方法将原集合包装为不可变集合
        Set<String> unmodifiableSet = Collections.unmodifiableSet(set);
        unmodifiableSet.forEach(log::info);

        // JDK 不可变集合对原集合的引用进行修改, 仍然可以更改元素, 这钟不可变需要保证原始集合没有人引用
        set.add("6");
        unmodifiableSet.forEach(log::info);

        // JDK 不可变集合虽然可以调用 add 方法, 但是会出现 UnsupportedOperationException 异常
        unmodifiableSet.add("7");
        unmodifiableSet.forEach(log::info);
    }
}