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
public class NioDatagramChannelB {

    public static void main(String[] args) throws IOException {
        nioDatagramChannelB();
    }


    private static void nioDatagramChannelB() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(true);

        // 监听来自 9001 端口的数据
        DatagramSocket datagramSocket = datagramChannel.socket();
        datagramSocket.bind(new InetSocketAddress(9001));

        // 向 9001 端口发送数据
        String content = "B 发来的消息";
        ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024);
        writeByteBuffer.put(content.getBytes());
        writeByteBuffer.flip();
        datagramChannel.send(writeByteBuffer, new InetSocketAddress("127.0.0.1", 9000));

        // 接收消息
        log.info("B 等待 A 发送的消息...");
        ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
        datagramChannel.receive(readByteBuffer);
        readByteBuffer.flip();
        log.info("B 收到 A 发送的消息 = {}", new String(readByteBuffer.array()));

    }

}
