package com.example.netty.io;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class PlainOioServer {

    public static void main(String[] args) throws Exception {
        new PlainOioServer().server(8080);
    }

    public void server(int port) throws Exception {
        final ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            //阻塞
            final Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + clientSocket);
            //业务逻辑处理异步
            new Thread(() -> {
                try (OutputStream out = clientSocket.getOutputStream()) {
                    while (true) {
                        Thread.sleep(10_000);
                        out.write("Hi\r\n".getBytes(StandardCharsets.UTF_8));
                        out.flush();
//                    clientSocket.close();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
//                    try {
//                        clientSocket.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }).start();
        }
    }
}
