package com.lupw.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author v_pwlu 2018/9/30
 */
@Slf4j
public class SocketServerMain {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            // 创建一个 ServerSocket, 在端口 8989 监听客户请求
            log.info("[服务端]");
            serverSocket =new ServerSocket(8989);

            Socket socket = serverSocket.accept();

            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String result = bufferedReader.readLine();
            bufferedReader.close();
            log.info("收到客户端消息 : {}", result);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
