package com.jimmy.simpleim.client.handler;

import com.jimmy.simpleim.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket packet) throws Exception {
        String userId = packet.getUserId();
        System.out.println("登陆成功，userId:" + userId);
    }
}
