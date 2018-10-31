package com.lupw.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author v_pwlu 2018/9/30
 */
@Slf4j
public class SocketClientMain {

    public static void  main(String[] args) {
        Socket socket = null;
        try {
            log.info("[客户端] 连接服务器");
            socket = new Socket("127.0.0.1", 8989);

            // 60s超时
            socket.setSoTimeout(60 * 1000);

            // 读取输入的数据
            log.info("[客户端] 等待输入信息");
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader sysBuff = new BufferedReader(new InputStreamReader(System.in));
            printWriter.println(sysBuff.readLine());
            printWriter.flush();
            sysBuff.close();
            log.info("[客户端] 信息已发送");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    log.info("[客户端] 断开连接");
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
