package com.spring.study.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author v_pwlu 2019/4/15
 */
@Slf4j
public class InsertSort {

    public static void main(String[] args) {
        int[] source = {7, 6, 5, 4, 3, 2, 1};
        insertSort(source);
    }

    private static void insertSort(int[] source) {
        // j = 当前循环需要进行比较的元素下标
        // orderValue = 需要进行排序的值
        int j, orderValue;
        for (int i = 1; i < source.length; i++) {
            orderValue = source[i];
            j = i - 1;
            while (j >= 0 && orderValue < source[j]) {
                source[j + 1] = source[j];
                j--;
            }
            source[j + 1] = orderValue;
            log.info("{}", Arrays.toString(source));
        }
    }
}
