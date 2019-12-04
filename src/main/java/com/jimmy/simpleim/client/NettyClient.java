package com.jimmy.simpleim.client;

import com.jimmy.simpleim.client.console.ConsoleCommandManager;
import com.jimmy.simpleim.client.console.LoginCommand;
import com.jimmy.simpleim.client.handler.HeartBeatResponseHandler;
import com.jimmy.simpleim.client.handler.HeartBeatTimer;
import com.jimmy.simpleim.client.handler.LoginResponseHandler;
import com.jimmy.simpleim.hanlder.IMIdleStateHandler;
import com.jimmy.simpleim.protocol.Spliter;
import com.jimmy.simpleim.protocol.PacketDecoder;
import com.jimmy.simpleim.protocol.PacketEncoder;
import com.jimmy.simpleim.server.handler.HeartBeatHandler;
import com.jimmy.simpleim.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class NettyClient {
    private static final String SERVER_ADDR = "127.0.0.1";
    private static final int PORT = 7000;

    public static void startClient() {
        EventLoopGroup g = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();

        b.group(g).channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IMIdleStateHandler());
                        pipeline.addLast(new Spliter());
                        pipeline.addLast(new PacketDecoder());
                        pipeline.addLast(new LoginResponseHandler());
                        pipeline.addLast(new HeartBeatResponseHandler());



                        pipeline.addLast(new PacketEncoder());
                        pipeline.addLast(new HeartBeatTimer());

                    }
                });
        b.connect(SERVER_ADDR, PORT).addListeners((future) -> {
            if (future.isSuccess()) {
                System.out.println("client连接server成功");
                Channel channel = ((ChannelFuture)future).channel();
                startCommand(channel);
            } else {
                System.out.println("client连接server失败");
            }
        });
    }

    public static void startCommand(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginCommand loginCommand = new LoginCommand();
        Scanner scanner = new Scanner(System.in);
        new Thread(() ->{
            while (!Thread.interrupted()) {
                if (SessionUtil.islLogin(channel)) {
                    consoleCommandManager.exec(scanner, channel);
                } else {
                    loginCommand.exec(scanner, channel);
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        NettyClient.startClient();
    }
}
