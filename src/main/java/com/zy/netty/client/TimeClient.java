package com.zy.netty.client;

import com.zy.netty.handler.TimeClientHandler;
import com.zy.netty.handler.TimeDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {

    public static void main(String[] args) {
        String ip = "192.168.0.113";
        int port = 8080;
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        client.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(final SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new TimeDecoder(), new TimeClientHandler());
                    }
                });

        try {
            ChannelFuture future = client.connect(ip, port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }

    }

}
