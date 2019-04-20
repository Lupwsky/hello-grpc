package com.lupw.guava.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author lupw 2019-03-11
 */
@Slf4j
@RestController
public class NioNetworkInterfaceController {

    @GetMapping(value = "/nio/networkinterface/test")
    public void test() {
        try {
            NetworkInterface firstNetworkInterface = null;
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (firstNetworkInterface == null) {
                    networkInterface = networkInterfaces.nextElement();
                    firstNetworkInterface = networkInterface;
                }

                log.info("网络接口名称 = {}", networkInterface.getName());
                log.info("网络接口显示名称 = {}", networkInterface.getDisplayName());
                log.info("网络接口的索引值 = {}", networkInterface.getIndex());
                log.info("网络接口 MAC 地址 = {}", networkInterface.getHardwareAddress());
                log.info("网络接口子的父 NetworkInterface 实例 (如果是物理接口, 为 null) = {}", networkInterface.getParent());
                log.info("网络接口是否是虚拟接口 (子接口) = {}", networkInterface.isVirtual());
                log.info("网络接口是否已经开启并运行 = {}", networkInterface.isVirtual());
                log.info("网络接口是回环网络接口 (通常我们访问的 127.0.0.1, 此 IP 一台机器上只有一个) = {}", networkInterface.isVirtual());
                log.info("---------------------------------------------------------------");

                // 获取 IP 地址信息
                Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses();
                while (addressEnumeration.hasMoreElements()) {
                    InetAddress inetAddress = addressEnumeration.nextElement();
                    log.info(inetAddress.getHostAddress());
                    log.info(inetAddress.getHostName());
                    log.info(inetAddress.getCanonicalHostName());
                }
                log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-");
            }

            if (firstNetworkInterface != null) {
                // 获取这个接口的所有有子接口
                Enumeration<NetworkInterface> subNetworkInterfaces = firstNetworkInterface.getSubInterfaces();
            }
        } catch (SocketException e) {
            log.info("NetworkInterface error = {}", e);
        }
    }

}
