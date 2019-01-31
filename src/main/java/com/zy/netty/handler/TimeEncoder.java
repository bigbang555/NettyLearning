package com.zy.netty.handler;

import com.zy.netty.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TimeEncoder extends MessageToByteEncoder {

    /* 通过ChannelOutboundHandlerAdapter
    @Override
    public void write(final ChannelHandlerContext ctx, final Object msg,
            final ChannelPromise promise)
            throws Exception {
        UnixTime time = (UnixTime) msg;
        ByteBuf encoded = ctx.alloc().buffer(4);
        encoded.writeLong(time.value());
        ctx.write(encoded, promise);
    }*/

    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o,
            final ByteBuf out)
            throws Exception {
        UnixTime time = (UnixTime) o;
        out.writeInt((int) time.value());
    }


}
