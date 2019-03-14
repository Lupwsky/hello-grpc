package com.lupw.guava.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static java.nio.channels.SelectionKey.OP_READ;

/**
 * @author v_pwlu 2019/3/14
 */
@Slf4j
public class NioServerSelector {

    // [NIO Selector 详解](https://segmentfault.com/a/1190000006824196)
    // 需要使用 Selector 类才能实现 I/0 多路复用

    public static void main(String[] args) throws IOException, InterruptedException {
        // 创建选择器实例
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // Channel 与 Selector 一起使用实现 I/0 多路复用，Channel 必须处于非阻塞模式下
        serverSocketChannel.configureBlocking(false);

        // 绑定端口号
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(9000));

        // 将 Channel 注册到选择器上, 第二个参数表示需要监听的 Channel 操作事件, 多个监听操作事件使用位或操作实现, 监听事操作件的类型如下
        // SelectionKey.OP_CONNECT, 表示客户端连接到服务器的监听事件
        // SelectionKey.OP_ACCEPT, 表示服务器接受客户端连接的监听事件
        // SelectionKey.OP_READ, 表示服务器准备从通道中读取数据的监听事件
        // SelectionKey.OP_WRITE, 表示服务器准备写入数据到通道的监听事件

        // SelectionKey 代表了注册到该 Selector 的 Channel, 可以从这里面获取到对应的 Channel 信息和绑定的 Selector 信息, 如监听了哪些事件
        // 服务端只能支持 OP_ACCEPT 监听事件, 使用 ServerSocketChannel.validOps() 可以知道对应的 Channel 支持的监听事件
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 调用 Selector.select() 方法, 返回发生对应监听事件的那些 Channel, 返回 int 表示有多少个 Channel 就绪了
        // select() 阻塞到至少有一个通道在你注册的事件上就绪了
        // select(long timeout) 阻塞到至少有一个通道在你注册的事件上就绪了, 设置最大阻塞时间
        // selectNow() 不阻塞, 如果没有 Channel 就绪, 返回 0, 这种方式需要不停循环检测
        while (true) {
            if (selector.select() == 0) {
                Thread.sleep(1000);
                continue;
            }

            // 获取已经准备就绪的 SelectionKey, 里面带有 Channel 信息和是那种操作事件已经就绪
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey tempSelectionKey = keyIterator.next();

                // 表示有客户端连接上来, 可以从 SelectionKey 取到 SocketChannel 实例, 并注册到选择器
                if (tempSelectionKey.isAcceptable()) {
                    SocketChannel clientChannel = ((ServerSocketChannel) tempSelectionKey.channel()).accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(tempSelectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                //
                if (tempSelectionKey.isReadable()) {
                    SocketChannel clientChannel = (SocketChannel) tempSelectionKey.channel();
                    ByteBuffer buf = (ByteBuffer) tempSelectionKey.attachment();
                    long bytesRead = clientChannel.read(buf);
                    if (bytesRead == -1) {
                        clientChannel.close();
                    } else if (bytesRead > 0) {
                        tempSelectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    }
                }

                if (tempSelectionKey.isValid() && tempSelectionKey.isWritable()) {
                    ByteBuffer buf = (ByteBuffer) tempSelectionKey.attachment();
                    buf.flip();
                    SocketChannel clientChannel = (SocketChannel) tempSelectionKey.channel();

                    clientChannel.write(buf);
                    if (!buf.hasRemaining()) {
                        tempSelectionKey.interestOps(OP_READ);
                    }
                    buf.compact();
                }

                // 删除, 表示这个 I/O 操作已经被处理了
                keyIterator.remove();
            }
        }
    }
}
