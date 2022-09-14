package com.example.netty.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NIOSocketServer {

    public static void main(String[] args) throws Exception {
        //创建ServerSocketChannel通道
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //创建Selector
        final Selector selector = Selector.open();
        //创建端口
        InetSocketAddress portAddress = new InetSocketAddress(9999);
        //绑定端口
        serverSocketChannel.bind(portAddress);
        //设置非阻塞 后续才可以使用selector
        serverSocketChannel.configureBlocking(false);
        //关注监听连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端连接
        while (true) {
            //如果这里返回值是0 则没有任何关注的事件发生
            //这个select方法是非阻塞的
            if (selector.select() == 0) {
                System.out.println("服务器等待客户端连接");
                continue;
            }
            final Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            final Iterator<SelectionKey> keyIterator = selectionKeySet.iterator();

            while (keyIterator.hasNext()) {
                final SelectionKey key = keyIterator.next();
                //监听客户端连接
                if (key.isAcceptable()) {
                    System.out.println("客户端已经连接");
                    //给该客户端生成一个对应的SocketChannel
                    final SocketChannel socketChannel = serverSocketChannel.accept();
                    final SelectableChannel channel = key.channel();
                    //socketChanneljava.nio.channels.SocketChannel
                    System.out.println("accept socketChannel=" + socketChannel + " hashCode=" + socketChannel.hashCode());
                    //channelsun.nio.ch.ServerSocketChannelImpl
                    System.out.println("accept channel=" + channel + " hashCode=" + channel.hashCode());
                    //设置非阻塞
                    socketChannel.configureBlocking(false);
                    //关注OP_READ事件
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                //客户端发送数据
                if (key.isReadable()) {
                    final SocketChannel socketChannel = (SocketChannel) key.channel();
                    System.out.println("read socketChannel=" + socketChannel + " hashCode=" + socketChannel.hashCode());
                    final ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    try {
                        final int read = socketChannel.read(byteBuffer);
                        System.out.println("read=" + read);
                        if (read == -1) {
                            //当读取长度为-1时，表示客户端断开连接，服务端应当关闭channel，或解除对 {@link SelectionKey.OP_READ} 的监听
                            socketChannel.close();
                        }
                        final int position = byteBuffer.position();
                        if (position > 0) {
                            //telnet默认是gbk编码的
                            System.out.println("服务器端接收到客户端数据:" + new String(byteBuffer.array(), 0, position, Charset.forName("GBK")));
                        }
                        //清除buffer
                        byteBuffer.clear();
                    } catch (IOException ioException) {
                        socketChannel.close();
                    }
                }
                //移除当前的key 防止重复操作
                keyIterator.remove();
            }
        }
    }
}
