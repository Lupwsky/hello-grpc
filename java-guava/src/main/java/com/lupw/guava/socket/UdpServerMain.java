package com.lupw.guava.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author lupw 2019-03-13
 */
@Slf4j
public class UdpServerMain {

    public static void main(String[] args) throws IOException {
        // 监听 9000 端口, 接收发送到 9000 端口的数据
        DatagramSocket datagramSocket = new DatagramSocket(9000);
        log.info("服务端绑定端口号 9000 成功");

        // 接收数据, DatagramPacket 的 length 参数要和客户端的一样
        byte[] byteArray = new byte[1024];
        while (true) {
            log.info("服务端正在接收数据");
            DatagramPacket datagramPacket = new DatagramPacket(byteArray, 1024);
            datagramSocket.receive(datagramPacket);
            String content = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
            log.info("收到客户端的消息 = {}", content);
        }

        // TODO 关闭资源
    }
}
