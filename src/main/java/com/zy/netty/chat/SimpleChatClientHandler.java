package com.zy.netty.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Horizon Time: 23:17 2019-02-03 Description:
 */
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)
            throws Exception {
        System.out.println(msg);
    }
}
