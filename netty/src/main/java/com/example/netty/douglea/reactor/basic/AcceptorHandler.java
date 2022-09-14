package com.example.netty.douglea.reactor.basic;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/***
 * 处理器: 处理新连接
 */
public class AcceptorHandler implements Runnable {


    Selector selector;

    ServerSocketChannel serverSocketChannel;

    public AcceptorHandler(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        try {
            //接收新连接
            SocketChannel socketChannel = serverSocketChannel.accept();
            //需要为新连接创建一个输入输出的handler
            if (socketChannel != null) {
                new EchoHandler(selector, socketChannel);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
