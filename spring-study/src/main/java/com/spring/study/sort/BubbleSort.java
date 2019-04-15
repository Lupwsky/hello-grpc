package com.spring.study.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author v_pwlu 2019/4/15
 */
@Slf4j
public class BubbleSort {

    public static void main(String[] args) {
        int[] source = {7, 6, 5, 4, 3, 2, 1};
        bubbleSort(source);
    }

    private static void bubbleSort(int[] source) {
        for (int i = 0; i < source.length - 1; i++) {
            for (int j = 0; j < source.length - 1 - i; j++) {
                if (source[j] > source[j + 1]) {
                    int temp = source[j];
                    source[j] = source[j + 1];
                    source[j + 1] = temp;
                }
            }
            log.info("{}", Arrays.toString(source));
        }
    }
}
