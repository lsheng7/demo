package com.example.netty.douglea.classic;

import com.example.netty.douglea.constant.SocketConstant;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionPerThread implements Runnable {

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(SocketConstant.SOCKET_SERVER_PORT);
            while (!Thread.interrupted()) {
                Socket socket = serverSocket.accept();
                //接收一个连接后 为socket连接 新建一个专属的处理器
                Handler handler = new Handler(socket);
                //创建新线程 专门负责一个连接的处理
                new Thread(handler).start();
            }
        } catch (IOException exception) {
            //处理异常
        }
    }
}
