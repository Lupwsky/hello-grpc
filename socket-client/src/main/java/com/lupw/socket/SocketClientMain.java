package com.lupw.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author v_pwlu 2018/9/30
 */
public class SocketClientMain {

    public static void  main(String[] args) {

        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8989);

            // 60s超时
            socket.setSoTimeout(60000);

            // 读取输入的数据
            PrintWriter printWriter =new PrintWriter(socket.getOutputStream(), true);
            BufferedReader sysBuff =new BufferedReader(new InputStreamReader(System.in));
            printWriter.println(sysBuff.readLine());

            // 刷新输出流, 使 server 马上收到该字符串
            printWriter.flush();
            sysBuff.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
