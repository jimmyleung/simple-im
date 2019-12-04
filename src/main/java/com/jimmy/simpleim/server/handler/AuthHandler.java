package com.jimmy.simpleim.server.handler;

import com.jimmy.simpleim.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        if (SessionUtil.islLogin(channel)) {
            channel.close();
        } else {
            channel.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }
}
