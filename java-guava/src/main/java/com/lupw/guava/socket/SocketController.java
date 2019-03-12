package com.lupw.guava.socket;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lupw 2019-03-12
 */
@Slf4j
@RestController
public class SocketController {

    @GetMapping(value = "/socket/service/test")
    public void testService() {

    }


    @GetMapping(value = "/socket/client/test")
    public void testClient() {

    }
}
