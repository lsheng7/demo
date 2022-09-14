package com.example.netty.nio.channel;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelReadDemo {

    public static void main(String[] args) {
        try (final FileInputStream fileInputStream = new FileInputStream("hello.txt")) {
            //限定ByteBuffer的size为文件大小
            ByteBuffer buffer = ByteBuffer.allocate(fileInputStream.available());
            final FileChannel fileChannel = fileInputStream.getChannel();
            fileChannel.read(buffer);
            buffer.flip();
            System.out.println(new String(buffer.array()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
