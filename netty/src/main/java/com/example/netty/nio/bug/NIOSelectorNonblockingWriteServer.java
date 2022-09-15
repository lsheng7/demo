package com.example.netty.nio.bug;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class NIOSelectorNonblockingWriteServer {

    private final static int MESSAGE_LENGTH = 1;

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8080), 50);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int count = selector.select();
            System.out.println("select event count:" + count);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 有客户端请求建立连接
                if (selectionKey.isAcceptable()) {
                    handleAccept(selectionKey);
                }
                // 有客户端发送数据
                else if (selectionKey.isReadable()) {
                    handleRead(selectionKey);
                }
                // 可以向客户端发送数据
                else if (selectionKey.isWritable()) {
                    handleWrite(selectionKey);
                }
                iterator.remove();
            }
        }
    }

    private static void handleAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        if (Objects.nonNull(socketChannel)) {
            System.out.println("receive connection from client. client:" + socketChannel.getRemoteAddress());
            // 设置客户端Channel为非阻塞模式，否则在执行socketChannel.read()时会阻塞
            socketChannel.configureBlocking(false);
            Selector selector = selectionKey.selector();
            socketChannel.register(selector, SelectionKey.OP_READ);
        }
    }

    private static void handleRead(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer readBuffer = ByteBuffer.allocate(MESSAGE_LENGTH);
        int length = 0;
        while (length < MESSAGE_LENGTH) {
            length += socketChannel.read(readBuffer);
        }
        System.out.println("receive message from client. client:" + socketChannel.getRemoteAddress() + " message length:{}" + readBuffer.position());
        ByteBuffer writeBuffer = ByteBuffer.allocate(readBuffer.position());
        readBuffer.flip();
        writeBuffer.put(readBuffer);
        // 读完数据后，为 SelectionKey 注册可写事件
        if (!isInterest(selectionKey, SelectionKey.OP_WRITE)) {
            selectionKey.interestOps(selectionKey.interestOps() + SelectionKey.OP_WRITE);
        }
        writeBuffer.flip();
        selectionKey.attach(writeBuffer);
    }

    // 服务端可能是为每个Channel维护一块缓冲区，当向某个Channel写数据时缓冲区满了，还可以向其他Channel写数据
    private static void handleWrite(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer writeBuffer = (ByteBuffer) selectionKey.attachment();
        int writeLength = socketChannel.write(writeBuffer);
        System.out.println("send message to client. client:" + socketChannel.getRemoteAddress() + " message length:{}" + writeLength);
        if (!writeBuffer.hasRemaining()) {
            // 写完数据后，要把写事件取消，否则当写缓冲区有剩余空间时，会一直触发写事件
            selectionKey.interestOps(selectionKey.interestOps() - SelectionKey.OP_WRITE);
            // socketChannel.shutdownOutput(); // channel调用shutdownOutput()后，会停止触发写事件
        }
    }

    // 判断 SelectionKey 对某个事件是否感兴趣
    private static boolean isInterest(SelectionKey selectionKey, int event) {
        int interestSet = selectionKey.interestOps();
        boolean isInterest = (interestSet & event) == event;
        return isInterest;
    }

}
