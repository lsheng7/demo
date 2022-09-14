package com.example.netty.nio.buffer;

import java.nio.IntBuffer;

public class BufferRead {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int index = 0; index < buffer.capacity(); index++) {
            buffer.put(index);
        }

        buffer = buffer.asReadOnlyBuffer();
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
        buffer.clear();
        //ReadOnlyBufferException
        //true
        System.out.println(buffer.isReadOnly());
        //class java.nio.HeapIntBufferR
        System.out.println(buffer.getClass());
        //只读buffer 不允许写入
        buffer.put(1);
    }
}
