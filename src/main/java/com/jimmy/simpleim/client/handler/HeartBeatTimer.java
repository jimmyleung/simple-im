package com.jimmy.simpleim.client.handler;

import com.jimmy.simpleim.protocol.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

public class HeartBeatTimer extends ChannelInboundHandlerAdapter {

    private static final int HEART_BEAT_INTERNAL = 5;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        startHeartBeat(ctx);
        super.channelActive(ctx);
    }

    private void startHeartBeat(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartBeatResponsePacket());
                // TODO why:
                startHeartBeat(ctx);
            }
        }, HEART_BEAT_INTERNAL, TimeUnit.SECONDS);

    }
}
