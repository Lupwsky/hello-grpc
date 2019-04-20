package com.lupw.guava.io;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author v_pwlu 2019/3/14
 */
@Slf4j
public class NioServerSocketChannel {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Java 提供了 ServerSocketChannel 和 SocketChannel 两个类实现 TCP 连接的 I/O 多路复用
        serverSocketChannel();
    }


    // ServerSocketChannel 类实现 BIO 效果

    private static void serverSocketChannel() throws IOException, InterruptedException {
        // 创建 ServerSocketChannel 实例
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // I/O 处理设置阻塞, 默认的方式也是阻塞, 多路复用中必须设置为 false
        serverSocketChannel.configureBlocking(true);

        // 创建 ServerSocket  实例
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(9000));

        log.info("服务端等待客户端连接...");
        SocketChannel socketChannel = serverSocketChannel.accept();

        if (socketChannel != null) {
            log.info("连接成功, 开始读取数据...");
            ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
            int len;
            while ((len = socketChannel.read(readByteBuffer)) != -1) {
                log.info("服务端收到到数据 = {}", new String(readByteBuffer.array(), 0, len));
                readByteBuffer.rewind();
            }
        } else {
            log.info("服务端等待客户端连接失败");
        }

        // TODO 关闭资源

        // ServerSocketChannel, SocketChannel 都可以设置为非阻塞模式
        // 调用 configureBlocking(), 参数设置为 false 即可
        // 设置成非阻塞模式, 如果没有客户端连接进来, 返回 null

        while (true) {
            SocketChannel channel = serverSocketChannel.accept();
            if (channel != null) {
                Thread.sleep(1000);
                log.info("过一段时间再去看看是否有客户端连接上了");
            } else {
                // TODO do something
            }
        }
    }
}
