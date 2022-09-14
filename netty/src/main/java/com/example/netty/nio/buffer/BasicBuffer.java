package com.example.netty.nio.buffer;

import java.nio.IntBuffer;

public class BasicBuffer {

    public static void main(String[] args) {
        //1. 创建Buffer 大小为5 即5个int类型的数据 5*4=20B
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //2. 向Buffer中存放数据
        intBuffer.put(10);
        intBuffer.put(15);
        intBuffer.put(19);
        intBuffer.put(13);
        intBuffer.flip();
        intBuffer.position(1);
        intBuffer.limit(3);
        //position < limit
        while (intBuffer.hasRemaining()) {
            System.out.println("position: " + intBuffer.position() + " limit: " + intBuffer.limit() + " capacity: " + intBuffer.capacity());
            //索引递增
            System.out.println(intBuffer.get());
        }
    }
}
