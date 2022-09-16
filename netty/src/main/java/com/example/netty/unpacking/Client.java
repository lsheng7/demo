package com.example.netty.unpacking;

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

/***
 * 拆包就是发送方发送一条数据，接收方分为多次接收了该条数据。比如A发送123456789，B接收到了两条消息12345、6789，而实际上这是一条消息的
 *
 * @author lvsheng
 * @version 1.0.0
 * @date 2022/09/16 10:53
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                    }
                })
                .connect(new InetSocketAddress("localhost", 8899));
        Channel channel = channelFuture.sync().channel();
        channel.writeAndFlush(ByteBufAllocator.DEFAULT.buffer().writeBytes(getContent()));
    }

    //获取要发送的内容
    private static byte[] getContent() {
        byte[] content = new byte[65];
        for (int i = 0; i < 65; i++) {
            content[i] = (byte) i;
        }
        return content;
    }
}
