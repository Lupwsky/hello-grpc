package com.spring.study.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author v_pwlu 2019/4/18
 */
@Slf4j
public class SelectionSort {

    public static void main(String[] args) {
        int[] source = {6, 5, 1, 3, 2, 4, 7, 8};
        selectionSort(source);
    }

    private static void selectionSort(int[] source) {
        log.info("{}", Arrays.toString(source));
        int min, temp;
        for (int i = 0; i < source.length; i++) {
            // 初始化未排序序列中最小数据数组下标
            min = i;
            for (int j = i + 1; j < source.length; j++) {
                // 在未排序元素中继续寻找最小元素，并保存其下标
                if (source[j] < source[min]) {
                    min = j;
                }
            }

            // 将未排序列中最小元素放到已排序列末尾
            if (min != i) {
                temp = source[min];
                source[min] = source[i];
                source[i] = temp;
            }
            log.info("{}", Arrays.toString(source));
        }
    }
}
