package com.example.netty.douglea.classic;

import io.netty.util.CharsetUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/***
 * 内容回显到客户端
 */
public class Handler implements Runnable {

    final Socket socket;

    Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                byte[] input = new byte[1024];
                //读取数据
                final int readLength = socket.getInputStream().read(input);
                //处理业务逻辑 获取处理结果
                //写入结果
                final OutputStream outputStream = socket.getOutputStream();
                final String readMsg = new String(input, 0, readLength, CharsetUtil.UTF_8);
                System.out.println(readMsg);
                String writeMsg = "\r\nserver reply: " + readMsg + "\r\n";
                outputStream.write(writeMsg.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            } catch (IOException exception) {
                //处理异常
            }
        }
    }
}
