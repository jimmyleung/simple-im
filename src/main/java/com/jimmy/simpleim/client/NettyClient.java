package com.jimmy.simpleim.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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

    public static void startCommand(Channel future) {

    }

    public static void main(String[] args) {
        NettyClient.startClient();
    }
}
