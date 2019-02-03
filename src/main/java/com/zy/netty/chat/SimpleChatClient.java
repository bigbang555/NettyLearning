package com.zy.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Horizon Time: 23:22 2019-02-03 Description:
 */
public class SimpleChatClient {


    private final String host;
    private final int port;


    public SimpleChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap();
            client.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer());
            ChannelFuture future = client.connect(host, port).sync();
            Channel channel = future.channel();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                channel.writeAndFlush(br.readLine() + "\r\n");
            }
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new SimpleChatClient("localhost", 8080).run();
    }
}
