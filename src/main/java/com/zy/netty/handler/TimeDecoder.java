package com.zy.netty.handler;

import com.zy.netty.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class TimeDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf in,
            final List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
