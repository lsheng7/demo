package com.example.netty.handler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/***
 * 服务器
 *
 * @author lvsheng
 * @version 1.0.0
 * @date 2022/09/16 10:35
 */
public class Server {

    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        /***
                         * LineBasedFrameDecoder构造参数的意义
                         * 第一个参数1000 表示这个handler解析的帧的最大长度
                         * 第二个参数false 表示的是解析出来的消息是否不包含换行分隔符、
                         * 第三个参数true  表示当解析超过最大帧长度时还未遇到换行分隔符，是否要报错
                         */
                        pipeline.addLast(new LineBasedFrameDecoder(1000, true, true));
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                        pipeline.addLast(new ServerHandler());
                    }
                })
                .bind(8899);
    }
}
