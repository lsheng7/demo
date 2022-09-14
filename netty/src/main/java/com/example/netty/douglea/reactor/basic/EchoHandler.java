package com.example.netty.douglea.reactor.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class EchoHandler implements Runnable {

    //处理器实例的状态：发送和接收，一个连接对应一个处理器实例
    static final int READ = 0;
    static final int WRITE = 1;
    final SocketChannel socketChannel;
    final SelectionKey selectionKey;
    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    int state = READ;

    //构造器
    EchoHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);
        //取得选择键，再设置感兴趣的IO事件
        selectionKey = socketChannel.register(selector, 0);
        //将Handler自身作为选择键的附件，一个连接对应一个处理器实例
        selectionKey.attach(this);
        //注册Read就绪事件
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            if (state == WRITE) {
                //发送状态，把数据写入连接通道
                socketChannel.write(byteBuffer);
                //byteBuffer切换成写模式，写完后，就准备开始从通道读
                byteBuffer.clear();
                //注册read就绪事件，开始接收客户端数据
                selectionKey.interestOps(SelectionKey.OP_READ);
                //修改状态，进入接收状态
                state = READ;
            } else if (state == READ) {
                //接收状态，从通道读取数据
                int length;
                while ((length = socketChannel.read(byteBuffer)) > 0) {
                    System.out.println(new String(byteBuffer.array(), 0, length));
                }
                //读完后，翻转byteBuffer的读写模式
                byteBuffer.flip();
                //准备写数据到通道，注册write就绪事件
                selectionKey.interestOps(SelectionKey.OP_WRITE);
                //注册完成后，进入发送状态
                state = WRITE;
            }
            //处理结束了，这里不能关闭select key，需要重复使用
            //sk.cancel()
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
