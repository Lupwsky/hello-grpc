package com.lupw.guava.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.*;

/**
 * @author lupw 2019-03-04
 */
@Slf4j
@RestController
public class NioBufferController {

    @GetMapping(value = "/nio/buffer/test")
    public void bufferTest() {
        bufferTest4();
    }

    // # NIO 中的缓存类 API 使用
    // 和传统 I/O 只能在处理 byte 和 char 型数据, NIO 支持多种类型的数据 (底层还是 byte 字节来处理), NIO 使用 Buffer 类处理不同的的数据类型
    // Buffer 是一个抽象类, 有 7 个直接的之类
    // ByteBuffer, CharBuffer, DoubleBuffer, FloatBuffer, IntBuffer, LongBuffer, ShortBuffer
    // 注意这 7 个类也是抽象类, 不能直接实例化, 但是这些类都提供了一个 wrap 方法将数据放入缓存中, 并获取实例
    // 实例化方法一, 使用已经存在的数组: ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{'1', '2', '3', '4', '5'});
    // 实例化方法二, 使用 allocate 方法: ByteBuffer byteBuffer = ByteBuffer.allocate(10);

    // # Buffer 类中的的方法
    // 先来看看 Buffer 中的方法, 如下图表所示:

    // # 缓冲区中的四个核心点
    // capacity (容量), limit (限制), position (位置), mark (标记)
    // 大小关系为: 0 <= mark <= position <= limit <= capacity

    // capacity: 表示缓存最大存放的元素的数量, 缓存被创建后, 这个值不可变
    // limit: 第一个不可写入或者读取的位置, 例如一个 ByteBuffer 的 limit 是 5, 那么表示这个缓存的 0 - 4 个字节可以用来存储数据, 从第 5 个字节开始就不能被写入和读取
    // position: 当前读取到的位置
    // mark: 标记位

    // 如: ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{'1', '2', '3', '4', '5'});
    // 初始值: capacity = 5, limit = 5, position = 0, mark = -1, 且初始值为 1, 2, 3, 4, 5, capacity 和 limit 和传入的数组大小一样大

    // # capacity()
    // capacity() 方法返回容量的大小

    public void bufferTest1() {
        // 此方法创建的 Buffer 的 capacity = 5, limit = 5
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{'1', '2', '3', '4', '5'});
        log.info("capacity = {}", byteBuffer.capacity());
    }

    // # limit() 和 limit(int)
    // 获取 limit 的大小和重新设置 limit 值
    // 注意由于 limit 的值是可以动态设置的, 如果新设置的 limit 的值小于当前 position 的值, 此时会将 position 的值设置为新的 limit 值

    public void bufferTest2() {
        CharBuffer charBuffer = CharBuffer.wrap(new char[]{'a', 'b', 'c', 'd'});
        // 可以获取到 charBuffer[3] 的值
        log.info("charBuffer[3] = {}", charBuffer.get(3));

        // 重新设置 limit 为 4, 读取 charBuffer[3] 出现异常
        charBuffer.limit(3);
        log.info("charBuffer[4] = {}", charBuffer.get(3));
    }

    // # position() 和 position(int)
    // position() 方法可以获取到下一个将要写入到 Buffer 中或者读取 Buffer 位置的索引值, 如果 position 已经最大了, 继续放入值则会报异常
    // position(int) 方法则用于设置下一个将要写入到 Buffer 中或者读取 Buffer 位置的索引值

    public void bufferTest3() {
        CharBuffer charBuffer = CharBuffer.wrap(new char[4]);

        // 此时 position 为 0, charBuffer 由于没有存储值, 获取的值全是空值
        log.info("position = {}", charBuffer.position());
        log.info("charBuffer1 = {}, {}, {}, {}", charBuffer.get(0), charBuffer.get(1), charBuffer.get(2), charBuffer.get(3));

        // charBuffer 的值变成了 1, 2, 3, 4
        charBuffer.put("1");
        charBuffer.put("2");
        charBuffer.put("3");
        charBuffer.put("4");
        log.info("charBuffer2 = {}, {}, {}, {}", charBuffer.get(0), charBuffer.get(1), charBuffer.get(2), charBuffer.get(3));

        // 再添加一个 A, 出现异常
        try {
            charBuffer.put("A");
        } catch (Exception e) {
            log.error("出现异常啦2 = {}", e);
        }

        // 设置 position 的值为 0, 在存入值 A, charBuffer 的值变成了 A, 2, 3, 4, 在存入值 A 后的 position 值为 1
        charBuffer.position(0);
        charBuffer.put("A");
        log.info("charBuffer3 = {}, {}, {}, {}", charBuffer.get(0), charBuffer.get(1), charBuffer.get(2), charBuffer.get(3));
        log.info("position = {}", charBuffer.position());
    }

    // # mark()
    // mark(int) 方法用于在缓冲区中设置标记, 常和 reset() 方法配合使用, 调用 reset() 后会将 mark 值存入 position 中
    // 注意 mark 的值默认是 -1, 如果没有设置 mark 的值, 调用 reset() 方法会报错, 不能将 -1 值设置给 position
    // 注意由于 position 和 limit 的值可以被动态调整, 如果已经设置了 mark 值, 在调整 limit 或者 position 值后如果小于 mark 值, mark 值将会被自定抛弃并重置为 -1=
    // 注意 mark 的值在 API 中被设计为不可能设置为大于 position, mark 被设计成像是探险时设置的路标, 目的是方便返回时找到回去的路, 你不可能在你没到达某个目的地前就能在这个目的地设置一个路标

    public void bufferTest4() {
        CharBuffer charBuffer = CharBuffer.wrap(new char[4]);
        charBuffer.put('1');
        charBuffer.put('2');

        // 设置标记, 将当前 position 的值给 mark
        log.info("position = {}", charBuffer.position());
        charBuffer.mark();

        charBuffer.put('3');
        charBuffer.put('4');

        // charBuffer 的值为 1, 2, 3, 4, 调用 reset(), 将 mark 的值给 position, 再新添加值 A
        log.info("charBuffer = {}, {}, {}, {}", charBuffer.get(0), charBuffer.get(1), charBuffer.get(2), charBuffer.get(3));

        // charBuffer 的值变成了 1, 2, A, 4
        charBuffer.reset();
        charBuffer.put("A");
        log.info("charBuffer = {}, {}, {}, {}", charBuffer.get(0), charBuffer.get(1), charBuffer.get(2), charBuffer.get(3));

        // 下列操作, charBuffer 的值变成了 1, 2, C, 4
        charBuffer.position(2);
        charBuffer.mark();
        charBuffer.put("C");
        log.info("charBuffer = {}, {}, {}, {}", charBuffer.get(0), charBuffer.get(1), charBuffer.get(2), charBuffer.get(3));
    }

    // remaining()
    // 返回剩余可读或者可写的空间, 他的值等于 limit - position

    // # isReadOnly()
    // 是否时只读的缓存区

    // isDirect()
    // 是否是使用的直接缓存区, 通常我们使用的 Buffer 类, 我们成为直接缓存区,
    // 在 JVM 中还有有一个中间缓存, 缓存读数据的过程, 将硬盘中的的数据拷贝到中间缓存, 然后从中间缓存中拷贝数据, 写也是这样一个操作过程的, 如果有大量的读写, 这样会大大降低对数据的处理
    // 直接使用直接缓冲区, 跳过将数据拷贝到中间缓存区这一步, 可以大大提高对数据的处理能力, 这种技术称之为零拷贝 [浅析Linux中的零拷贝技术]https://www.jianshu.com/p/fad3339e3448
    // [零拷贝底层实现原理](https://juejin.im/entry/59b740fdf265da06633d02cf)

    // Buffer 类中只有 ByteBuffer 可以使用直接缓存区

    public void bufferTest5() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
        byteBuffer.put((byte) 1);
        log.info("isDirect = {}", byteBuffer.isDirect());
    }

    // clear()
    // 将缓冲区还原为初始状态, 这个初始状态是 position = 0, limit= capacity, mark= -1, 要注意这个方法只是将缓冲区还原为初始状态, 缓存里面的数据并不会被擦除

    // flip()
    // 调用该方法调用该方法会令 limit = position, position = 0, mark = -1
    // 常用于在写入数据到缓存后调用该方法从缓存中读取数据

    public void bufferTest6() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.flip();
        log.info("byteBuffer[0] = {}", byteBuffer.get());
        log.info("byteBuffer[1] = {}", byteBuffer.get());
        log.info("byteBuffer[2] = {}", byteBuffer.get());
    }
}
