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
            List<String> addressList  = new ArrayList<>();
            InetAddress[] inetAddressList = InetAddress.getAllByName(hostName);
            for (InetAddress inetAddress : inetAddressList) {
                addressList.add(inetAddress.getHostAddress());
            }
            log.info("[获取IP信息] hostName = {}, address = {}", hostName, addressList.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // 在大多数机器上都可以取到本机 IP, 多网卡时获取就会出现问题
        try {
            log.info("[获取IP信息] IP = {}", InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // 多网卡时可以获取本机 IP, 但是如果对虚拟网卡会获取到虚拟网卡, 可能不能获取到正确的 IP
        try {
            log.info("[获取IP信息] IP = {}", getLocalHostLANAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    /**
     * https://www.cnblogs.com/starcrm/p/7071227.html
     *
     * @return
     * @throws UnknownHostException
     */
    private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    // 排除loopback类型地址
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}