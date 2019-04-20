package com.lupw.guava.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lupw 2019-03-12
 */
@Slf4j
public class ServerMain {

    public static void main(String[] args) {
        try {
            // 创建 ServerSocket 实例, 绑定端口号, 并设置监听队列
            log.info("服务端初始化...");
            ServerSocket serverSocket = new ServerSocket(9000);
            log.info("服务端初始化完成");

            // 等待客户端连接, 该方法会一直阻塞, 直到有客户端接入
            log.info("等待客户端连接");
            Socket socket = serverSocket.accept();
            log.info("客户端连接成功");

            // 接收数据, read 方法会阻塞, 直到读取数据
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String content;
            while (true) {
                content = readLine(bufferedReader);
                log.info("服务端收到数据 = {}", content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭各种连接和资源
        }
    }

    private static String readLine(BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int value = -1;
        while ((value = bufferedReader.read()) != -1) {
            if (value == 'r') {
                bufferedReader.mark(1);
                if (bufferedReader.read() != 'n') {
                    bufferedReader.reset();
                }
                break;
            }
            // 字符流读取到值可以直接使用 char 强转
            stringBuilder.append((char) value);
        }
        return stringBuilder.toString();
    }
}
