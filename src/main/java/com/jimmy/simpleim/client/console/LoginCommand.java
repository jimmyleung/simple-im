package com.jimmy.simpleim.client.console;

import com.jimmy.simpleim.protocol.request.LoginRequestPacket;
import com.jimmy.simpleim.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        if (SessionUtil.islLogin(channel)) {
            return;
        }
        System.out.println("请输入用户名");
        String userName = scanner.nextLine();
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserName(userName);
        packet.setPassword("p");
        channel.writeAndFlush(packet);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
