package com.thread.excutor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author v_pwlu 2018/10/11
 */
public class List2StringUtils {

    private List2StringUtils() {
        throw new UnsupportedOperationException();
    }


    /**
     * List 转换成指定符号分隔的字符串
     *
     * @param dataList 源数据
     * @param split 分隔符
     * @return 指定符号分隔的字符串
     */
    public static String list2String(List<String> dataList, String split) {
        if (split == null) {
            throw new RuntimeException("分隔符号参数 split 不能为 null");
        }
        return dataList.stream().map(value -> value == null ? "" : value).collect(Collectors.joining(split));
    }


    /**
     * List 转换成指定符号分隔的字符串
     *
     * @param dataList 源数据
     * @param split 分隔符
     * @param split 前缀符号
     * @param split 后缀符号
     * @return 指定符号分隔的字符串
     */
    public static String list2String(List<String> dataList, String split, String prefix, String suffix) {
        if (split == null) {
            throw new RuntimeException("分隔符号参数 split 不能为 null");
        }

        if (prefix == null) {
            throw new RuntimeException("前缀符号参数 prefix 不能为 null");
        }

        if (suffix == null) {
            throw new RuntimeException("后缀符号参数 suffix 不能为 null");
        }
        return dataList.stream().map(value -> value == null ? "" : value).collect(Collectors.joining(split, prefix, suffix));
    }
}
