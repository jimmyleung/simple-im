package com.jimmy.simpleim.server.handler;

import com.jimmy.simpleim.protocol.request.LoginRequestPacket;
import com.jimmy.simpleim.protocol.response.LoginResponsePacket;
import com.jimmy.simpleim.util.Session;
import com.jimmy.simpleim.util.SessionUtil;
import com.jimmy.simpleim.util.UIDUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket packet) throws Exception {
        String userName = packet.getUserName();
        String password = packet.getPassword();
        if (validPassword(userName, password)) {
            LoginResponsePacket response = new LoginResponsePacket();
            String userId = UIDUtil.generateId();
            response.setUserId(userId);
            response.setUserName(userName);
            response.setSuccess(true);
            Session session = new Session(userId, userName);
            SessionUtil.bindSession(session, ctx.channel());
            System.out.println("用户登陆成功，userId:" + userId);
            ctx.writeAndFlush(response);
        }
    }

    public boolean validPassword(String userName, String password) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unbindSession(ctx.channel());
    }
}
