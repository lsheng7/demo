package com.example.netty.nio.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/***
 * 内存映射的字节缓冲区
 *
 * 可以让文件直接在内存(堆外内存)中修改 即操作系统不需要进行一次数据拷贝
 *
 * @author lvsheng
 * @version 1.0.0
 * @date 2022/08/31 15:51
 */
public class MappedByteBufferDemo {

    public static void main(String[] args) throws IOException {
        //1. 关联文件
        final RandomAccessFile randomAccessFile = new RandomAccessFile("hello.txt", "rw");
        //2. 获取通道
        final FileChannel fileChannel = randomAccessFile.getChannel();
        //3. 获取内存映射
        //mode: 操作模式 READ_WRITE
        //position: 可以操作起始位置
        //size: 映射到内存的大小(0->5]即5个字节长度 并不是索引长度
        //可以直接修改的范围是0-5 最大值是: java.lang.Integer#MAX_VALUE
        //2^32次方B=2^22KB=2^12MB=2^2GB 最大4GB
        MappedByteBuffer mappedByteBuffer = fileChannel.map(MapMode.READ_WRITE, 0, 5);
        //class java.nio.DirectByteBuffer
        System.out.println(mappedByteBuffer.getClass());
        //第1个字节修改为R
        mappedByteBuffer.put(0, (byte) 'R');
        //第2个字节修改为W
        mappedByteBuffer.put(3, (byte) 'W');
        mappedByteBuffer.put(4, (byte) 'B');
        //IndexOutOfBoundsException
        mappedByteBuffer.put(5, (byte) 'T');
        randomAccessFile.close();
    }
}
