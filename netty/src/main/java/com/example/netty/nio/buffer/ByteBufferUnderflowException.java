package com.example.netty.nio.buffer;

import java.nio.ByteBuffer;

public class ByteBufferUnderflowException {

    public static void main(String[] args) {
//        ByteBuffer buffer = ByteBuffer.allocate(400);
//        buffer.putInt(1);
//        buffer.putLong(2L);
//        buffer.putChar('A');
//        buffer.putInt(4);
//        buffer.flip();
//        final int anInt = buffer.getInt();
//        final long aLong = buffer.getLong();
//        final char aChar = buffer.getChar();
//        //put和get数据类型不一致 int是4个字节 long是8字节
//        //BufferUnderflowException
//        final long aLong1 = buffer.getLong();
//        System.out.println("anInt=" + anInt);
//        System.out.println("aLong=" + aLong);
//        System.out.println("aChar=" + aChar);
//        System.out.println("aLong1=" + aLong1);

        ByteBuffer buffer = ByteBuffer.allocate(2);
        //BufferOverflowException int=4b>2b
        buffer.putInt(1);
    }
}
