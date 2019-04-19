package com.lupw.guava.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author v_pwlu 2019/3/12
 */
@Slf4j
@RestController
public class BioController {

    @GetMapping(value = "/bio/test")
    public void test() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("D:\\Work\\hello-grpc\\java-guava\\src\\main\\resources\\md\\file_channel_test.txt");
            // 字节输入流转换成字符输入流, 并指定编码集, 不指定默认使用 UTF-8
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            // 转换成 BufferedReader 方便数据的读取, 每次读取一行
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String readValue;
            while ((readValue = bufferedReader.readLine()) != null) {
                stringBuilder.append(readValue);
            }

            String value = stringBuilder.toString();
            log.info("length = {}, value = {}", value.length(), value);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    // 关闭流
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
