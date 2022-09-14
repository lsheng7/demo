package com.example.netty.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelCopyDemo {

    public static void main(String[] args) {
        try (FileInputStream fileInputStream = new FileInputStream("hello.txt");
                FileOutputStream fileOutputStream = new FileOutputStream("world.txt")) {
            final FileChannel inputStreamChannel = fileInputStream.getChannel();
            final FileChannel outputStreamChannel = fileOutputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (inputStreamChannel.read(buffer) != -1) {
                //读写切换
                buffer.flip();
                //写入目标文件
                outputStreamChannel.write(buffer);
                //所有相关属性复位 如果这里不复位 那么read()始终时0 会导致死循环 并且数据重复读取
                buffer.clear();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
