package com.lupw.guava.io;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author v_pwlu 2019/3/15
 */
@Slf4j
public class NioClientSelector {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9000));
        socketChannel.configureBlocking(false);

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String content = "你好, 服务器" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
                log.info("客户端发送消息 = {}", content);
                sendMsg(socketChannel, content);
            }
        }).start();


        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("客户端收到消息" + receiveMsg(socketChannel));
            }
        }
        ).start();
    }


    private static void sendMsg(SocketChannel socketChannel, String msg){
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            log.info("客户端发送消息失败, error = {}", e);
        }
    }

    private static String receiveMsg(SocketChannel socketChannel){
        StringBuilder contentBuffer = new StringBuilder();
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count;
            while((count = socketChannel.read(buffer)) > 0){
                contentBuffer.append(new String(buffer.array(), 0, count));
            }
        } catch (IOException e) {
            log.info("客户端接收消息失败 = {}, error = {}", e);
            try {
                socketChannel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return contentBuffer.toString();
    }
}
