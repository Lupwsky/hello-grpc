package com.spring.study.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author v_pwlu 2019/4/17
 */
@Slf4j
public class MedianSort {


    public static void main(String[] args) {
        // [6, 5, 1, 3, 2, 4, 7]
        int[] source = {6, 5, 1, 3, 2, 4, 7};
        medianSort(source);
    }

    private static void medianSort(int[] source) {
        // [6, 5, 1, 3, 2, 4, 7]
        log.info("1 = {}", Arrays.toString(source));
        // 找到需要排序数组的中值, 中值为 4, 在 source[5] 的位置
        int size = source.length;
        int medianValue = 4;
        int medianValueIndex = 5;

        // 找到数组中间元素的位置
        int midIndex = source.length / 2;

        // [6, 5, 1, 4, 2, 3, 7]
        // 中值和中间位置元素比较, 并交换
        if (source[medianValueIndex] > source[midIndex]) {
            int tempValue = source[midIndex];
            source[midIndex] = source[medianValueIndex];
            source[medianValueIndex] = tempValue;
        }
        log.info("2 = {}", Arrays.toString(source));

        // 进行比较, 比中值大的元素放到右边, 比中值小的元素放到左边
        for (int i = 0;  i < midIndex; i++) {
            for (int j = midIndex + 1; j < size; j ++) {
                if (source[i] > medianValue && source[j] < medianValue) {
                    int temp = source[i];
                    source[i] = source[j];
                    source[j] = temp;
                }
            }
        }
        log.info("3 = {}", Arrays.toString(source));
    }
}
