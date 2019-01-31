package com.zy.netty.handler;

import com.zy.netty.pojo.UnixTime;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        UnixTime time = (UnixTime) msg;
        System.out.println(time);
        ctx.close();
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
