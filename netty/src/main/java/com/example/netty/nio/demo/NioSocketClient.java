package com.example.netty.nio.demo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;

public class NioSocketClient {

    static Selector selector = null;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey next : selectionKeys) {
                if (next.isConnectable()) {
                    System.out.println("开始客户端链接");
                    System.out.println("next="+next.hashCode());
                    socketChannel.finishConnect();
                    System.out.println("socketChannel#hashCode=" + socketChannel.hashCode());
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                } else if (next.isWritable()) {
                    System.out.println("请输入要发送的信息:");
                    System.out.println("socketChannel#hashCode=" + socketChannel.hashCode());
                    System.out.println("next="+next.hashCode());
                    String result = scanner.next();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    byteBuffer.put(result.getBytes("GBK"));
                    byteBuffer.flip();
                    socketChannel.write(byteBuffer);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("结束~！！！~~~~");
                } else if (next.isReadable()) {
                    System.out.print("服务端返回的消息:");
                    System.out.println("socketChannel#hashCode=" + socketChannel.hashCode());
                    System.out.println("next="+next.hashCode());
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int read = socketChannel.read(byteBuffer);
                    System.out.println(new String(byteBuffer.array(), 0, read));
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                }
            }
        }
    }
}
