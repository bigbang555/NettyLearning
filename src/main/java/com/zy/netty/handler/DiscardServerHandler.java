package com.zy.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        ByteBuf in = (ByteBuf) msg;

        try {
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            //丢弃接受到的数据
            ReferenceCountUtil.release(msg);
        }
    }


    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause)
            throws Exception {
        cause.printStackTrace();
        //当错误发生，中断连接
        ctx.close();
    }
}
