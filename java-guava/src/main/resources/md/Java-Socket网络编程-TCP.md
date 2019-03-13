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

# 完整的接口一条消息

上面的例子中, 看日志似乎时一行一行的再读取数据, 其实并不是, 第一个原因客户端发送信息的时间间隔比较长, 第二个原因是服务端接收数据的 char 数组比每次发送过来的消息要大, 将服务端接收数据的 char 数组改小一点, 比客户端发送的消息要小, 示例代码如下:

```java
int len;
char[] contentChars = new char[2];
while ((len = bufferedReader.read(contentChars)) != -1) {
    log.info("服务端收到数据 = {}", new String(contentChars, 0, len));
}
```

服务端输出结果如下:

```text
09:44:50.121 [main] INFO com.lupw.guava.socket.ServerMain - 服务端初始化...
09:44:50.126 [main] INFO com.lupw.guava.socket.ServerMain - 服务端初始化完成
09:44:50.126 [main] INFO com.lupw.guava.socket.ServerMain - 等待客户端连接
09:45:12.155 [main] INFO com.lupw.guava.socket.ServerMain - 客户端连接成功
09:45:17.263 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 你好
09:45:17.265 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = , 
09:45:17.265 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 来自
09:45:17.265 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 客户
09:45:17.265 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 端的
09:45:17.265 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 问候
09:45:17.265 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = , 
09:45:17.265 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 时间
09:45:17.265 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 =  =
09:45:17.265 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 =  2
09:45:17.265 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 01
09:45:17.265 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 9-
09:45:17.265 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 03
09:45:17.265 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = -1
09:45:17.265 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 3 
09:45:17.266 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 09
09:45:17.266 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = :4
09:45:17.266 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 5:
09:45:17.266 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 17
```

可以看到, 本来是完整的一条消息, 接收的时候是按照每两个字符的接收, 并非是一条完整的消息了, 要每次读取一次完整的消息, 就需要定义客户端和服务端发送和接收数据时的协议, 以特定标识开头和特定标识结尾的才算是一条完整的消息, 否则认为这条消息不是一个完整的消息, 这个时候服务端可以丢弃这个消息并发送一个消息告诉客户端发送的消息失败了, 下面的例子, 当每次接收到连续 rn 标识表示接收到一条消息

客户端发送消息, 末尾加上 "rn":
 
```java
// 发送数据, 主动刷新缓存才会发送
OutputStream outputStream = socket.getOutputStream();
OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
while (true) {
    Thread.sleep(5000);
    log.info("开始发送数据 = {}", "你好, 来自客户端的问候, 时间 = " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
    bufferedWriter.write("你好, 来自客户端的问候, 时间 = " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + "rn");
    bufferedWriter.flush();
}
```

服务器端接收消息, 接收到 "rn" 认为时一条完整消息:

```java
// 接收数据
InputStream inputStream = socket.getInputStream();
InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
String content;
while (true) {
    content = readLine(bufferedReader);
    log.info("服务端收到数据 = {}", content);
}

// 收到连续 `rn` 标识表示接收到一条消息
private static String readLine(BufferedReader bufferedReader) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    int value = -1;
    while ((value = bufferedReader.read()) != -1) {
        if (value == 'r') {
            bufferedReader.mark(1);
            if (bufferedReader.read() != 'n') {
                bufferedReader.reset();
            }
            break;
        }
        // 字符流读取到值可以直接使用 char 强转
        stringBuilder.append((char) value);
    }
    return stringBuilder.toString();
}
```

服务端输出的结果如下:

```text
11:33:22.740 [main] INFO com.lupw.guava.socket.ServerMain - 服务端初始化...
11:33:22.745 [main] INFO com.lupw.guava.socket.ServerMain - 服务端初始化完成
11:33:22.745 [main] INFO com.lupw.guava.socket.ServerMain - 等待客户端连接
11:33:36.510 [main] INFO com.lupw.guava.socket.ServerMain - 客户端连接成功
11:33:41.569 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 你好, 来自客户端的问候, 时间 = 2019-03-13 11:33:41
11:33:46.569 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 你好, 来自客户端的问候, 时间 = 2019-03-13 11:33:46
11:33:51.570 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 你好, 来自客户端的问候, 时间 = 2019-03-13 11:33:51
11:33:56.570 [main] INFO com.lupw.guava.socket.ServerMain - 服务端收到数据 = 你好, 来自客户端的问候, 时间 = 2019-03-13 11:33:56
```

上面只是一个简单的示例, 实际开发是会有更多细节的地方这里并没有体现, 例如检测发送消息的长度, 特殊字符的检测, 消息接收失败的处理, 服务端实现使用多线程处理允许多个客户端连接等等

# 
















