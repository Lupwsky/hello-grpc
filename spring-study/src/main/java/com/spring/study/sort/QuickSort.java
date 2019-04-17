package com.spring.study.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author v_pwlu 2019/4/17
 */
@Slf4j
public class QuickSort {

    public static void main(String[] args) {
        int[] source = {6, 5, 1, 3, 2, 4, 7, 8};
        quickSort(source, 0, source.length - 1);
    }


    private static void quickSort(int[] a, int left, int right) {
        int i , j, t, temp;
        if(left > right) {
            return;
        }

        // temp 中存的就是基准数
        temp = a[left];
        i = left;
        j = right;
        while(i != j) {
            // 先从右边往左边开始找小于基准值的数
            while(a[j] >=temp && i < j) {
                j--;
            }

            // 再从左边往右边开始找大于基准值的数
            while(a[i] <= temp && i < j) {
                i++;
            }

            // 交换两个数在数组中的位置
            if(i<j) {
                t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }

        // 最终将基准数归位
        a[left] = a[i];
        a[i] = temp;

        // 继续处理左边的, 这里是一个递归的过程
        quickSort(a, left,i - 1);

        // 继续处理右边的, 这里是一个递归的过程
        quickSort(a, i + 1,right);
    }
}
