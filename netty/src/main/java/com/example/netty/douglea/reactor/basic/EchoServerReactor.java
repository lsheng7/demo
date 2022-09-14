package com.example.netty.douglea.reactor.basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/***
 * 单线程Reactor
 */
public class EchoServerReactor implements Runnable {

    final Selector selector;

    final ServerSocketChannel serverSocketChannel;

    //构造函数
    public EchoServerReactor() throws IOException {
        //打开选择器 serverSocketChannel连接监听通道
        selector = Selector.open();
        //注册serverSocketChannel的accept新连接接收事件
        serverSocketChannel = ServerSocketChannel.open();
        //必须设置成非阻塞的
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 9090));
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //将新连接处理器作为附件 绑定到SelectionKey上
        selectionKey.attach(new AcceptorHandler(selector, serverSocketChannel));
    }

    @Override
    public void run() {
        //选择器轮询
        try {
            while (!Thread.interrupted()) {
                selector.select();
                final Set<SelectionKey> selectionKeys = selector.selectedKeys();
                final Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    //反应器负责dispatch收到的事件
                    final SelectionKey eventKey = iterator.next();
                    dispatch(eventKey);
                }
                selectionKeys.clear();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void dispatch(SelectionKey eventKey) {
        Runnable handler = (Runnable) eventKey.attachment();
        if (handler != null) {
            handler.run();
        }
    }
}
