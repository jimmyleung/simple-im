package com.jimmy.simpleim.hanlder;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public class IMIdleStateHandler extends IdleStateHandler {
    private static final int READER_IDLE_TIME_SECONDS = 60;

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME_SECONDS, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println(READER_IDLE_TIME_SECONDS + " 秒没有请求，断开连接");
        ctx.channel().close();
    }
}
