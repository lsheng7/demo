package com.example.netty.nio.buffer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.stream.Stream;

/***
 * Scatthering: 将数据写入到buffer中,可以采用buffer数组 依次写入[分散]
 * Gathering: 从Buffer读取数据时,可以采用buffer数组 依次读取[聚合]
 *
 * @author lvsheng
 * @version 1.0.0
 * @date 2022/08/31 19:31
 */
public class ScattheringAndGathering {

    public static void main(String[] args) throws Exception {
        //使用ServerSocketChannel和SocketChannel 网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        final InetSocketAddress inetSocketAddress = new InetSocketAddress(7777);
        //绑定端口到socket并启动
        serverSocketChannel.socket().bind(inetSocketAddress);
        //创建一个buffer数组
        final ByteBuffer[] data = new ByteBuffer[2];
        data[0] = ByteBuffer.allocate(5);
        data[1] = ByteBuffer.allocate(3);
        //等待客户端连接(telnet)
        final SocketChannel socketChannel = serverSocketChannel.accept();

        //假定从客户端接收8个字节
        int messageLength = 8;
        //循环读取数据
        while (true) {
            int readByte = 0;
            while (readByte < messageLength) {
                final long read = socketChannel.read(data);
                //累计读取的字节数
                readByte += read;
                System.out.println("read byte: " + readByte);
                //使用流打印
                Stream.of(data).forEach(buffer -> {
                    System.out.println("buffer.position= " + buffer.position() + " buffer.limit= " + buffer.limit());
                });
                Stream.of(data).forEach(ByteBuffer::flip);
                //将数据读出显示到客户端
                int writeByte = 0;
                while (writeByte < messageLength) {
                    final long write = socketChannel.write(data);
                    writeByte += write;
                }
                //所有的buffer进行复位
                Stream.of(data).forEach(ByteBuffer::clear);
                System.out.println("readByte=" + readByte + " writeByte=" + writeByte);
            }
        }
    }
}
