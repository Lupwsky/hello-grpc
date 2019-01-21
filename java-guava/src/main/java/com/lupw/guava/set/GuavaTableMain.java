package com.lupw.guava.set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

/**
 * @author v_pwlu 2019/1/17
 */
@Slf4j
public class GuavaTableMain {

    public static void main(String[] args) {
        tableTest();
    }


    private static void tableTest() {
        // 通常我们选择使用诸如 Map<String, Map<String, String>> 这样的数据结构类型来实现多个键做索引获取值的情况
        // 通过第一个 key 和第二个 key 一同确认最终需要的值, 无论是获取值和存入值都是不便于处理的
        // Guava 提供了新的集合类型 Table 来实现多个键做索引, 可以友好的处理多个键做索引获取值的需求

        Table<String, String, String> hashBasedTable = HashBasedTable.create();
        hashBasedTable.put("R1", "C1", "R1C1");
        hashBasedTable.put("R1", "C2", "R1C2");
        hashBasedTable.put("R1", "C3", "R1C3");
        hashBasedTable.put("R2", "C1", "R2C1");
        hashBasedTable.put("R2", "C2", "R2C2");
        hashBasedTable.put("R2", "C3", "R2C3");
        hashBasedTable.put("R3", "C1", "R3C1");
        hashBasedTable.put("R3", "C2", "R3C2");
        hashBasedTable.put("R3", "C3", "R3C3");

        log.info(hashBasedTable.get("R2", "C2"));
        log.info("------------------------------------------------");

        // rowMap() 方法将 Table 转换成 Map<T, Map<T, T>> 的数据结构类型, 第一个键值为行, 第二个键值为列, 对这个结果集的操作同样会同步到到 Table 中
        Map<String, Map<String, String>> rowMapMap = hashBasedTable.rowMap();
        rowMapMap.forEach((key, value) -> {
            log.info("key = {}, value = {}", key, value);
        });
        log.info("------------------------------------------------");

        // rowMap() 方法将 Table 转换成 Map<T, Map<T, T>> 的数据结构类型, 第一个键值为列, 第二个键值为行, 对这个结果集的操作同样会同步到到 Table 中
        Map<String, Map<String, String>> columnMapMap = hashBasedTable.columnMap();
        columnMapMap.forEach((key, value) -> {
            log.info("key = {}, value = {}", key, value);
        });
        log.info("------------------------------------------------");

        // row(key) 方法返回给定行的值集合, 数据结构类型为 Map<T, T>, 对这个结果集的操作同样会同步到到 Table 中
        Map<String, String> columnMap = hashBasedTable.row("R1");
        columnMap.forEach((key, value) -> {
            log.info("key = {}, value = {}", key, value);
        });
        log.info("------------------------------------------------");

        // column(key) 方法返回给定列的值集合, 数据结构类型为 Map<T, T>, 对这个结果集的操作同样会同步到到 Table 中
        Map<String, String> rowMap = hashBasedTable.column("C1");
        rowMap.forEach((key, value) -> {
            log.info("key = {}, value = {}", key, value);
        });
        log.info("------------------------------------------------");

        // cellSet() 获取集合
        Set<Table.Cell<String, String, String>> cellSet = hashBasedTable.cellSet();
        cellSet.forEach(cell -> {
            log.info("rowKey = {}, columnKey = {}, value = {}", cell.getRowKey(), cell.getColumnKey(), cell.getValue());
        });
        log.info("------------------------------------------------");

        // 其他
        // HashBasedTable: 本质上用 HashMap<R, HashMap<C, V>> 实现
        // TreeBasedTable: 本质上用 TreeMap<R, TreeMap<C,V>> 实现
        // ImmutableTable: 本质上用 ImmutableMap<R, ImmutableMap<C, V>> 实现, 注: ImmutableTable 对稀疏或密集的数据集都有优化
        // ArrayTable: 要求在构造时就指定行和列的大小, 本质上由一个二维数组实现, 以提升访问速度和密集 Table 的内存利用率
    }
}
