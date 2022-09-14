package com.example.netty.douglea.classic;

public class Server {

    public static void main(String[] args) {
        new Thread(new ConnectionPerThread()).start();
    }
}
