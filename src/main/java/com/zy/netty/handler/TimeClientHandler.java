package com.zy.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Date;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        try {
            long time = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(time));
            ctx.close();
        } finally {
            buf.release();
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
