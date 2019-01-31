package com.zy.netty.server;

import com.zy.netty.handler.TimeEncoder;
import com.zy.netty.handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    private int port;

    public NettyServer(final int port) {
        this.port = port;
    }

    public void run() {
        //接受进来的连接
        EventLoopGroup boss = new NioEventLoopGroup();
        //处理已经被接受的连接
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(final SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new TimeEncoder(), new TimeServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.SO_KEEPALIVE, true);
        try {
            // 绑定端口，开始接收进来的连接
            ChannelFuture future = server.bind(port).sync();
            System.out.println("Netty服务已启动");
            //等待服务器  socket关闭
            //在这个例子中，这不会发生，但你可以优雅地关闭你的服务器
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }

    }


    public static void main(String[] args) {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new NettyServer(port).run();
    }

}
