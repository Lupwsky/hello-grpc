package com.spring.study;

import lombok.extern.slf4j.Slf4j;

/**
 * @author v_pwlu 2019/3/25
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        // count++ 和 ++count, 先使用, 再加 1, 先加 1, 再使用,
        // 这里的使用不是使用 count 值, 使用的是 count++ 这个表达式的值, count++ 和 ++count 表达式不仅仅是让 count 自增, 并且是有返回值的, count++ 返回的是 count 自增之前的值, ++count 返回的是 count 自增后的值
        // 对于 count = count++ 这个表达式, count 先自增 1, count 的值为 1, 然后将 count++ 的返回的值赋值给 count,
        // count++ 返回的是 count 自增前的值, 因此 count 虽然自增了 1 , 但是在这一步又被赋值为 0, 因此循环结束后 count 的值还是 0

        int count = 0;
        for (int i = 0; i < 10; i++) {
            count = count++;
        }

        // 对于表达式 count++, JVM 做的操作是如下:
        // *　JVM 把 count 值拷贝到临时变量中
        // *　JVM 将 count 值加 1
        // *　JVM 返回临时变量的值
    }
}
