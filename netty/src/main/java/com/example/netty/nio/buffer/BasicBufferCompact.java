package com.example.netty.nio.buffer;

import java.nio.IntBuffer;

public class BasicBufferCompact {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(10);
        //填充10个数据
        for (int index = 0; index < intBuffer.capacity(); index++) {
            intBuffer.put(index);
        }
        intBuffer.flip();
        //读取4个数据
        for (int index = 0; index < 4; index++) {
            System.out.println(intBuffer.get());
        }
        System.out.println("=================================");
        //剩余6个数据 压缩
        intBuffer.compact();
        intBuffer.flip();
        //读取剩余的数据
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
