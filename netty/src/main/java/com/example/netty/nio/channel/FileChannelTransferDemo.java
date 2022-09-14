package com.example.netty.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileChannelTransferDemo {

    public static void main(String[] args) {
        try (FileInputStream fileInputStream = new FileInputStream("hello.txt");
                FileOutputStream fileOutputStream = new FileOutputStream("welcome.txt")) {
            final FileChannel inputStreamChannel = fileInputStream.getChannel();
            final FileChannel outputStreamChannel = fileOutputStream.getChannel();
            //数据转储
            inputStreamChannel.transferTo(0, inputStreamChannel.size(), outputStreamChannel);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
