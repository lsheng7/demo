package com.example.netty.nio.bug;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TimeServer {

    private int port;

    public TimeServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new TimeServer(8888).service();
    }

    public void service() {
        ServerSocketChannel ssc = null;
        try {
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(port), 10);
            Selector selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("service run.....");
            while (true) {
                if (selector.select() == 0) {
                    continue;
                }
                Iterator<SelectionKey> i = selector.selectedKeys().iterator();
                while (i.hasNext()) {
                    SelectionKey selectionKey = i.next();
                    i.remove();
                    if (selectionKey.isAcceptable()) {
                        SocketChannel sc = ((ServerSocketChannel) selectionKey.channel()).accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_WRITE);
                    } else if (selectionKey.isWritable()) {
                        System.out.println("writing");
                        final SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024 * 1024 * 8);
                        for (int index = 0; index < 1024 * 1024 * 8; index++) {
                            writeByteBuffer.put((byte) 5);
                        }
                        writeByteBuffer.flip();
                        int length;
                        int total=0;
                        while (writeByteBuffer.hasRemaining()) {
                            length = socketChannel.write(writeByteBuffer);
                            total += length;
                            if (length < 0) {
                                throw new EOFException();
                            }
                        }
                        //8,388,608
                        System.out.println(total);
                    } else if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.read(buffer);
                        System.out.println("reading");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ssc != null) {
                try {
                    ssc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}