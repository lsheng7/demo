package com.example.netty.nio.chat;

import cn.hutool.core.util.ObjectUtil;
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

/**
 * 聊天服务器
 *
 * @author Administrator
 * @date 2022/10/10
 */
public class ChatServer {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress( 9999));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (true) {
            if (selector.select() == 0) {
                System.out.println("等待事件发生");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            if (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("客户端[remoteAddress=" + socketChannel.getRemoteAddress() + "]上线了");
                }
                if (next.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) next.channel();
                    System.out.println(selector.keys().size());
                    int read = socketChannel.read(byteBuffer);
                    if (read > 0) {
                        System.out.println("客户端传递的内容为:" + new String(byteBuffer.array(), 0, read, Charset.forName("GBK")));
                        for (SelectionKey selectionKey : selector.keys()) {
                            SelectableChannel channel = selectionKey.channel();
                            if (channel instanceof SocketChannel && ObjectUtil.notEqual(channel, socketChannel)) {
                                byteBuffer.flip();
                                ((SocketChannel) channel).write(byteBuffer);
                            }
                        }
                    }else if(read==-1){
                        //telnet
                        System.out.println("客户端已经离线");
                        //取消注册
                        next.cancel();
                    }
                    byteBuffer.clear();
                }
                //如果不移除在下次  selector.selectedKeys();时候会被重复处理 导致报错
                iterator.remove();
            }
        }
    }
}
