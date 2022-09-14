package com.example.netty.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * 网站服务器
 * 这段代码片段只能同时处理一个连接
 * 要处理多个并发的客户端 需要每个Socket客户端创建一个新的Thread
 * 但是这一种方式 会导致如下问题:
 * 1. 即使JVM在物理上可以支持大量的线程 但是大量线程存在 会出现线程切换性能开销
 * 2. 存在大量的线程处于休眠状态 只是等待输入或输出数据就绪 是一种资源浪费
 * 3. 需要为每个线程调用的栈分配内存 默认值范围是64KB到1MB 具体取决于操作系统
 * @author lvsheng
 * @version 1.0.0
 * @date 2022/08/25 10:33
 */
public class SiteServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8088);
            //这是一个阻塞的方法
            Socket clientSocket = serverSocket.accept();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream())) {
                String request, response;
                //readLine也是一个阻塞的方法 直到有一个换行符或者回车结尾的字符串被读取
                while ((request = in.readLine()) != null) {
                    if ("shutdown".equals(request)) {
                        break;
                    }
                    response = processRequest(request);
                    out.println(response);
                    out.flush();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static String processRequest(String request) {
        System.out.println("server receive msg:" + request);
        return "server reply msg:".concat(request);
    }
}
