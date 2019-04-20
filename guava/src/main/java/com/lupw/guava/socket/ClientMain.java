package com.lupw.guava.socket;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @author lupw 2019-03-12
 */
@Slf4j
public class ClientMain {

    public static void main(String[] args) {
        try {
            // 创建 Socket 实例, 并发起连接
            log.info("客户端初始化...");
            Socket socket = new Socket("127.0.0.1", 9000);
            log.info("连接服务端成功");

            // 发送数据, 主动刷新缓存才会发送
            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            while (true) {
                Thread.sleep(5000);
                log.info("开始发送数据 = {}", "你好, 来自客户端的问候, 时间 = " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                bufferedWriter.write("你好, 来自客户端的问候, 时间 = " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + "rn");
                bufferedWriter.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接和各种资源
        }
    }

}
