package com.thread.excutor;

import lombok.extern.slf4j.Slf4j;

import java.nio.Buffer;
import java.nio.CharBuffer;
import java.util.Arrays;

/**
 * @author v_pwlu 2018/9/19
 */
@Slf4j
public class IOMain {
    // 在 Java 中的 Buffer 类就对应着内存中缓冲区, Buffer 本质上就是一块内存区, 可以用来写入数据和读取数据,
    // 这块内存被 Buffer 类进行了封装, 对外提供一系列的读写方便操作缓冲区的 API, Buffer 的直接子类如下图所示:

    public static void main(String[] args) {
        // Buffer 是一个抽象类, 先来看看 Buffer 类的源码, 首先可以看到 Buffer 类里面定义了四个属性, 这也是所有缓冲区所具备的, 如下代码所示:
        // mark: position 的备注位置, 只有手动掉用 mark 方法将才会将当前 position 的位置记录到 mark 中, reset 方法则可将当前记录的 mark 值赋值给 position
        //       [https://blog.csdn.net/yiifaa/article/details/76223070]
        // position: 下一个被写或者被读取的元素的位置的索引, 这个值在元素被读取和被写入后自动的更新, 也可以使用 position 方法主动更新
        // limit: 缓冲区中能被读取的元素的上界值, 例如缓冲区容量为 10, limit 为 5, 那么只能读取前 5 个元素, 读取第六个元素时将会出现异常
        // capacity: 缓冲区的容量, 这个值一旦被确定后不允许被改变

        CharBuffer buffer = CharBuffer.allocate(100);
        buffer.put('A').put('B').put('C').flip();
        String result = buffer.toString();
        log.info("[MAIN] result = {}", result);
    }
}
