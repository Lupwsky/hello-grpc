package com.lupw.guava.io;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author v_pwlu 2019/3/14
 */
@Slf4j
public class NioSocketChannel {

    public static void main(String[] args) throws IOException, InterruptedException {
        socketChannel();
    }

    private static void socketChannel() throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(true);

        log.info("开始连接到服务端...");
        // 开启了阻塞模式, 这里在连接的会阻塞知道连接成功或者失败
        boolean connectResult = socketChannel.connect(new InetSocketAddress("127.0.0.1", 9000));
        if (connectResult) {
            log.info("连接到服务端成功");

            while (true) {
                Thread.sleep(1000);
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                String content = "发送当前时间吧, " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss:SSS");
                log.info("开始发送消息 = {}", content);
                byteBuffer.put(content.getBytes());
                byteBuffer.flip();

                socketChannel.write(byteBuffer);
                byteBuffer.rewind();
            }
        } else {
            log.info("连接到服务端失败");
        }

        // TODO 关闭资源

        // 非阻塞模式
        while (true) {
            boolean result = socketChannel.connect(new InetSocketAddress("127.0.0.1", 9000));
            // 或者调用 socketChannel.finishConnect() 来判断是否连接成功
            if (!result) {
                Thread.sleep(1000);
                log.info("等会再看看是否连接到服务器成功");
            } else {
                // TODO do something
            }
        }
    }
}
