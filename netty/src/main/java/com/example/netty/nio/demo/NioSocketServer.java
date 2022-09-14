package com.example.netty.nio.demo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioSocketServer {

    static Selector selector = null;
    static ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //必须设置成非阻塞的
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 9999));
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            if (selector.select() == 0) {
                System.out.println("未获取到客户端链接当前线程非阻塞运行");
                continue;
            }
            System.out.println("有连接进来了");
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                //客户端发起连接
                if (next.isAcceptable()) {
                    acceptAdapter(next);
                    System.out.println("客户端连接后,注册的key数量=" + selector.keys().size());
                } else if (next.isReadable()) {
                    readAdapter(next);
                } else if (next.isWritable()) {
                    witerAdapter(next);
                }
                iterator.remove();
            }
        }
    }

    public static void acceptAdapter(SelectionKey selectionKey) throws Exception {
        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
        System.out.println(channel);
        SocketChannel socketChannel = channel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("socketChannel#hashCode="+socketChannel.hashCode());
        System.out.println("当前客户端已经绑定到选择器READ事件中" + socketChannel.getRemoteAddress().toString());
    }

    public static void readAdapter(SelectionKey selectionKey) throws Exception {
        final Object attachment = selectionKey.attachment();
        System.out.println("attachment value:" + attachment);
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        byteBuffer.clear();
        int read = socketChannel.read(byteBuffer);
        byteBuffer.flip();
        System.out.println("socketChannel#hashCode="+socketChannel.hashCode());
        System.out.println("客户端传递的内容为:" + new String(byteBuffer.array(), 0, read));
        socketChannel.register(selector, SelectionKey.OP_WRITE);
    }

    public static void witerAdapter(SelectionKey selectionKey) throws Exception {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        byteBuffer.clear();
        byteBuffer.put("收到消息了".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        socketChannel.register(selector, SelectionKey.OP_READ);
        selectionKey.attach("1234");
        System.out.println("socketChannel#hashCode="+socketChannel.hashCode());
    }
}