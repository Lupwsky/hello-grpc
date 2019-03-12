---
title: Java-Socket网络编程-TCP
date: 2019-03-12 22:11:49
categories: Java
---

TCP 是专门设计用于在不可靠的英特网上提供可靠的端到端的字节流通信的协议, 是一个面向连接的协议, Java 为 TCP 协议提供了两个 Socket 类: ServerSocket 和 Socket, ServerSocket 代表 TCP 协议中的一个服务器端, Socket 代表 TCP 中的一个客户端, Socket 用于实现服务器端和客户端的数据通信 (使用 TCP 或者 UDP), 一个完整的 Socket 编程流程图如下 (图片来源: NIO 与 Socket 编程技术指南):

<img src="Java-Socket网络编程-TCP/WechatIMG219.png" width="500" height="700"/>

<!-- more -->

# 服务端实现简单示例

按照上面的 Socket 编程流程图实现一个服务端, 不停的接收客户端发送的数据并打印出来, 示例代码如下:

```java
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
```

这段代码需要注意的是, `不能使用 BufferedReader.readLine() 读取, 使用 readLine() 读取只有在 BufferedReader 断开的时候才能读取到数据`, 会一直阻塞在这里

# 客户端实现简单示例

同样按照Socket 编程流程图实现一个服务端, 每隔 5 秒发送信息, 实例代码如下:

```java
public static void main(String[] args) {
    try {
        // 创建 Socket 实例, 并发起连接
        log.info("客户端初始化...");
        Socket socket = new Socket("127.0.0.1", 9000);
        log.info("连接服务端成功");

        // 发送数据, 主动刷新缓存才会发送
        OutputStream outputStream = socket.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        while (true) {
            Thread.sleep(5000);
            log.info("开始发送数据 = {}", "你好, 来自客户端的问候, 时间 = " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            bufferedWriter.write("你好, 来自客户端的问候, 时间 = " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            bufferedWriter.flush();
        }
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    } finally {
        // 关闭连接和各种资源
        // bufferedWriter.close();
        // outputStreamWriter.close();
        // outputStream.close();
        // socket.close();
    }
}
```

# 测试结果

服务端输出结果如下:

```text
23:49:44.840 [main] INFO com.lupw.guava.socket.ServerMain - 服务端初始化...
23:49:44.862 [main] INFO com.lupw.guava.socket.ServerMain - 服务端初始化完成
23:49:44.862 [main] INFO com.lupw.guava.socket.ServerMain - 等待客户端连接
23:49:48.385 [main] INFO com.lupw.guava.socket.ServerMain - 客户端连接成功
23:49:53.465 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 你好, 来自客户端的问候, 时间 = 2019-03-12 23:49:53
23:49:58.470 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 你好, 来自客户端的问候, 时间 = 2019-03-12 23:49:58
23:50:03.475 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 你好, 来自客户端的问候, 时间 = 2019-03-12 23:50:03
23:50:08.476 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 你好, 来自客户端的问候, 时间 = 2019-03-12 23:50:08
23:50:13.481 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 你好, 来自客户端的问候, 时间 = 2019-03-12 23:50:13
```

客户端输出结果如下:

```text
23:49:48.355 [main] INFO com.lupw.guava.socket.ClientMain - 客户端初始化...
23:49:48.385 [main] INFO com.lupw.guava.socket.ClientMain - 连接服务端成功
23:49:53.462 [main] INFO com.lupw.guava.socket.ClientMain - 开始发送数据 = 你好, 来自客户端的问候, 时间 = 2019-03-12 23:49:53
23:49:58.469 [main] INFO com.lupw.guava.socket.ClientMain - 开始发送数据 = 你好, 来自客户端的问候, 时间 = 2019-03-12 23:49:58
23:50:03.475 [main] INFO com.lupw.guava.socket.ClientMain - 开始发送数据 = 你好, 来自客户端的问候, 时间 = 2019-03-12 23:50:03
23:50:08.476 [main] INFO com.lupw.guava.socket.ClientMain - 开始发送数据 = 你好, 来自客户端的问候, 时间 = 2019-03-12 23:50:08
23:50:13.481 [main] INFO com.lupw.guava.socket.ClientMain - 开始发送数据 = 你好, 来自客户端的问候, 时间 = 2019-03-12 23:50:13
```

# 