package com.zy.netty.handler;

import com.zy.netty.pojo.UnixTime;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        final ChannelFuture future = ctx.writeAndFlush(new UnixTime());
        future.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
