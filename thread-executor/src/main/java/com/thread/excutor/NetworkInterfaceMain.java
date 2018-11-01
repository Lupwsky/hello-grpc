package com.thread.excutor;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.net.*;
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
            // 通常情况下, 即使主机没有任何其他网络连接, 回环接口也总是存在的, IPv4 = 127.0.0.1, IPv6 = 0:0:0:0:0:0:0:1
            if (networkInterfaceEnumeration == null) {
                log.info("[获取IP信息] 没有获取到IP信息");
            } else {
                while (networkInterfaceEnumeration.hasMoreElements()) {
                    NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
                    // 网络名称
                    String networkName = networkInterface.getName();

                    // IP 版本
                    String ipVersion = "NONE";
                    List<String> hostNameList = new ArrayList<>();
                    List<String> addressList  = new ArrayList<>();
                    Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
                    while(inetAddressEnumeration.hasMoreElements()) {
                        InetAddress inetAddress = inetAddressEnumeration.nextElement();
                        if (inetAddress instanceof Inet4Address) {
                            ipVersion = "v4";
                        } else if(inetAddress instanceof Inet6Address) {
                            ipVersion = "v6";
                        } else {
                            ipVersion = "NONE";
                        }
                        hostNameList.add(inetAddress.getHostName());

                        // IPv4 是点分形式, IPv6 是冒号分隔的 16 进制形式
                        addressList.add(inetAddress.getHostAddress());
                    }
                    log.info("[获取IP信息] ipVersion = {}, networkName = {}, hostName = {}, address = {}",
                            ipVersion, networkName, hostNameList.toString(), addressList.toString());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // 通过 hostName 获取地址信息
        String hostName = "www.baidu.com";
        try {
            String ipVersion = "NONE";
            List<String> addressList  = new ArrayList<>();
            InetAddress[] inetAddressList = InetAddress.getAllByName(hostName);
            for (InetAddress inetAddress : inetAddressList) {
                if (inetAddress instanceof Inet4Address) {
                    ipVersion = "v4";
                } else if(inetAddress instanceof Inet6Address) {
                    ipVersion = "v6";
                } else {
                    ipVersion = "NONE";
                }
                addressList.add(inetAddress.getHostAddress());
            }
            log.info("[获取IP信息] ipVersion = {}, hostName = {}, address = {}",
                    ipVersion, hostName, addressList.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
