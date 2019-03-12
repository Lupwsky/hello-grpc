package com.lupw.guava.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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


            // 不能使用 readLine() 读取, 使用 readLine() 读取只有在 BufferedReader 断开的时候才能读取到数据
            // String content = bufferedReader.readLine();
            // log.info("服务端收到数据 = {}", content);

            int len;
            char[] contentChars = new char[1024];
            while ((len = bufferedReader.read(contentChars)) != -1) {
                log.info("服务端收到数据 = {}", new String(contentChars, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭各种连接和资源
            // bufferedReader.close();
            // inputStreamReader.close();
            // inputStream.close();
            // socket.close();
            // serverSocket.close();
        }
    }

}
