package com.thread.excutor;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/9/19
 */
@Slf4j
public class NetworkInterfaceMain {

    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            // 通常情况下, 即使主机没有任何其他网络连接, 回环接口也总是存在的
            if (networkInterfaceEnumeration == null) {
                log.info("[获取IP信息] 没有获取到IP信息");
            } else {
                while (networkInterfaceEnumeration.hasMoreElements()) {
                    NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
                    // 网络名称
                    String networkName = networkInterface.getName();

                    List<String> addressList = new ArrayList<>();
                    Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
                    while(inetAddressEnumeration.hasMoreElements()) {
                        InetAddress inetAddress = inetAddressEnumeration.nextElement();
                        addressList.add(inetAddress.getHostName());
                    }
                    log.info("[获取IP信息] name = {}, address = {}", networkName, addressList.toString());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
