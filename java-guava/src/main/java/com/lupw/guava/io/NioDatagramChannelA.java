package com.lupw.guava.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author v_pwlu 2019/3/14
 */
@Slf4j
public class NioDatagramChannelA {

    public static void main(String[] args) throws IOException {
        nioDatagramChannelA();
    }


    private static void nioDatagramChannelA() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(true);

        // 监听来自 9000 端口的数据
        DatagramSocket datagramSocket = datagramChannel.socket();
        datagramSocket.bind(new InetSocketAddress(9000));

        log.info("A 等待 B 发送的消息...");
        ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
        datagramChannel.receive(readByteBuffer);
        readByteBuffer.flip();
        log.info("A 收到 B 发送的消息 = {}", new String(readByteBuffer.array()));

        // 向 9001 端口发送数据
        String content = "A 发来的消息";
        ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024);
        writeByteBuffer.put(content.getBytes());
        writeByteBuffer.flip();
        datagramChannel.send(writeByteBuffer, new InetSocketAddress("127.0.0.1", 9001));
    }

    // 可以将 DatagramChannel "连接" 到网络中的特定地址的, 由于 UDP 是无连接的, 连接到特定地址并不会像 TCP 通道那样创建一个真正的连接
    // 而是锁住 DatagramChannel ，让其只能从特定地址收发数据
    // 如 channel.connect(new InetSocketAddress("192.168.1.199", 8080))
    // 当连接后, 也可以使用 read() 和 write() 方法, 就像在用传统的通道一样, 只是在数据传送方面没有任何保证

}
