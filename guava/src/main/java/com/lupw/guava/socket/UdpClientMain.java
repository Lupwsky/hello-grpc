package com.lupw.guava.socket;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author lupw 2019-03-13
 */
@Slf4j
public class UdpClientMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 客户端监听端口号 9001, 接收发送到 9001 端口的数据
//        DatagramSocket datagramSocket = new DatagramSocket(9001);
//        log.info("客户端绑定端口号 9001 成功");

        // 将数据发送到指定主机和指定端口的服务端上
//        while (true) {
//            Thread.sleep(1000);
//            String content = "来自客户端的问候, time = " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
//            DatagramPacket datagramPacket = new DatagramPacket(content.getBytes(), content.getBytes().length, InetAddress.getLocalHost(), 9000);
//            datagramSocket.send(datagramPacket);
//            log.info("客户端发送消息成功, content = " + content);
//        }

        MulticastSocket multicastSocket = new MulticastSocket(9000);
        // 加入到组播
        multicastSocket.joinGroup(InetAddress.getByName("127.0.01"));
        byte[] byteArray = new byte[1024];
        while (true) {
            DatagramPacket datagramPacket = new DatagramPacket(byteArray, 1024);
            multicastSocket.receive(datagramPacket);
            String content = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
            log.info("收到客户端的消息 = {}", content);
        }
    }
}
