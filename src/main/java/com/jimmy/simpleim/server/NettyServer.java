package com.jimmy.simpleim.server;

import com.jimmy.simpleim.hanlder.IMIdleStateHandler;
import com.jimmy.simpleim.protocol.PacketCodecHandler;
import com.jimmy.simpleim.protocol.Spliter;
import com.jimmy.simpleim.server.handler.AuthHandler;
import com.jimmy.simpleim.server.handler.HeartBeatHandler;
import com.jimmy.simpleim.server.handler.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {

    private static final int PORT = 7000;

    public static void startServer() throws Exception{
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IMIdleStateHandler());
                            pipeline.addLast(new Spliter());
                            pipeline.addLast(new PacketCodecHandler());
                            pipeline.addLast(new LoginRequestHandler());
                            pipeline.addLast(new HeartBeatHandler());
                            pipeline.addLast(new AuthHandler());
                        }
                    });
            ChannelFuture f = b.bind(PORT).addListener((future) -> {
                if (future.isSuccess()) {
                    System.out.println("端口绑定成功:" + PORT);
                } else {
                    System.out.println("端口绑定失败:" + PORT);
                }
            });
            f.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        NettyServer.startServer();
    }
}
