package com.example.netty.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/***
 * 粘包就是发送方发送了多条数据，接收方读取数小于发送数。比如A发送123、456、789，B收到了两条消息，1234、56789等
 *
 * @author lvsheng
 * @version 1.0.0
 * @date 2022/09/16 10:26
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        //客户端
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                    }
                })
                .connect(new InetSocketAddress("localhost", 8899));

        Channel channel = channelFuture.sync().channel();
        channel.writeAndFlush(ByteBufAllocator.DEFAULT.buffer().writeBytes("hello\nworld\nwelcome\n".getBytes(StandardCharsets.UTF_8)));
    }
}
