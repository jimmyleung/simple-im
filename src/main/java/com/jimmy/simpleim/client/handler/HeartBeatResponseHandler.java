package com.jimmy.simpleim.client.handler;

import com.jimmy.simpleim.protocol.response.HeartBeatResponsePacket;
import com.jimmy.simpleim.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeartBeatResponseHandler extends SimpleChannelInboundHandler<HeartBeatResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatResponsePacket packet) throws Exception {
        Byte userId = packet.getVersion();
        System.out.println("登陆成功，userId:" + userId);
    }
}
