package com.example.netty.douglea.reactor.basic;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException {
        new Thread(new EchoServerReactor()).start();
    }
}
