package com.example.netty.nio.channel;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件通道演示-向文件中写入数据
 *
 * @author lvsheng
 * @version 1.0.0
 * @date 2022/08/30 17:18
 */
public class FileChannelWriteDemo {

    public static void main(String[] args) {

        File hello = new File("hello.txt");
        try (final FileOutputStream fileOutputStream = new FileOutputStream(hello)) {
            //真实的类型是FileChannelImpl
            final FileChannel channel = fileOutputStream.getChannel();
            //封装待写入的数据
//            ByteBuffer byteBuffer = ByteBuffer.wrap("hello world".getBytes());
            //将数据写入文件通道
//            channel.write(byteBuffer);
            final byte[] data = "hello world welcome to oceanus".getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(data.length);
            buffer.put(data);
            buffer.flip();
            channel.write(buffer);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
