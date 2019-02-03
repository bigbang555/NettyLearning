package com.zy.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by Horizon Time: 21:32 2019-02-03 Description:
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {


    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + "加入\n");
        }
        channels.add(incoming);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + "离开\n");
        }
        channels.remove(incoming);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)
            throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            if (incoming != channel) {
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + msg + "\n");
            } else {
                channel.writeAndFlush("[you]" + msg + "\n");
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClent:" + incoming.remoteAddress() + "在线\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "掉线\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" +incoming.remoteAddress()+"异常\n");
        cause.printStackTrace();
        ctx.close();
    }

}
