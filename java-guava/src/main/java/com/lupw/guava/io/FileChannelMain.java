package com.lupw.guava.io;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author v_pwlu 2019/3/14
 */
@Slf4j
public class FileChannelMain {

    public static void main(String[] args) throws IOException {
        // I/O -> I/O 流, NIO -> Channel (通道)
        // Java NIO 中的 FileChannel 是一个连接到文件的通道。可以通过文件通道读写文件, FileChannel 无法设置为非阻塞模式，它总是运行在阻塞模式下
        // 通过使用一个 InputStream、OutputStream 或 RandomAccessFile 来获取一个 FileChannel

        read();

        // 处理上面示例中出现的方法, 还有有一些常用的方法
        // FileChannel.size() 方法返回 Channel 所关联文件的大小
        // FileChannel.truncate() 方法截取一个文件, 指定长度外面的部分将被删除
        // FileChannel.position() 方法获取或者设置当前读写文件的位置, 新打开 Channel, 如果写文件不是 append 模式, position 是 0, 新写入的会覆盖原内容

        // [Java NIO (一) FileChannel 详解](https://blog.csdn.net/zrh_lawliet/article/details/81166028)
        // [Java NIO](http://xintq.net/2017/06/12/everything-about-java-nio/)
    }


    // 读取文件内容

    private static void read() throws IOException {
        RandomAccessFile readRandomAccessFile = new RandomAccessFile("D:\\Work\\hello-grpc\\java-guava\\src\\main\\resources\\md\\file_channel_test.txt", "rw");
        FileChannel readFileChannel = readRandomAccessFile.getChannel();
        ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
        int len;
        while ((len = readFileChannel.read(readByteBuffer)) != -1) {
            log.info("读取到数据");
            log.info(new String(readByteBuffer.array(), 0, len, StandardCharsets.UTF_8));
            // 重置缓存, 调用该方法缓存的 limit 和 capacity 不变, position = 0, mark = -1
            readByteBuffer.rewind();
        }
        log.info("position = {}", readFileChannel.position());
        log.info("size = {}", readFileChannel.size());
        // 关闭通道
        readFileChannel.close();
    }

    // 写文件内容, windows下的文本文件换行符: \r\n, linux/unix下的文本文件换行符 :\r, Mac下的文本文件换行符: \n

    private static void write() throws IOException {
        RandomAccessFile writeRandomAccessFile = new RandomAccessFile("D:\\Work\\hello-grpc\\java-guava\\src\\main\\resources\\md\\file_channel_test.txt", "rw");
        FileChannel writeFileChannel = writeRandomAccessFile.getChannel();
        String baseWriteContent = "写入测试数据";
        ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024);

        for (int i = 0; i < 5; i++) {
            String writeContent = baseWriteContent + DateTime.now().toString("yyyy-MM-dd HH:mm:ss:SSS") + System.lineSeparator();
            // 缓存中添加数据
            writeByteBuffer.put(writeContent.getBytes(StandardCharsets.UTF_8), 0, writeContent.getBytes().length);

            // 反转缓存区, limit = position, position = 0, capacity = -1
            // 这样就不会将 buf 其他为 0 的写入进去, 出现空写
            writeByteBuffer.flip();
            // 将缓存中的数据写入通道
            writeFileChannel.write(writeByteBuffer);
            // 将通道里尚未写入磁盘的数据强制写到磁盘上
            // 出于性能方面的考虑, 操作系统会将数据缓存在内存中, 只有缓存满时才会写到磁盘上
            // 如果需要即时的将数据写入到磁盘里面, 每次 write 后调用 force 方法即可, force 方法的参数表示是否将文件元数据 (如权限信息等) 写到磁盘上
            writeFileChannel.force(true);

            // 重置缓存区状态, limit = capacity, position = 0, mark = -1
            // 因为之前调用了 flip() 方法, 导致 limit 的值变化了, 为了下次循环缓存还能放入最多 1024 个字节从头开始写入数据, 调用 clear() 方法重置
            writeByteBuffer.clear();

            log.info("position = {}", writeFileChannel.position());
            log.info("size = {}", writeFileChannel.size());
        }
        // 关闭通道
        writeFileChannel.close();
    }

}
