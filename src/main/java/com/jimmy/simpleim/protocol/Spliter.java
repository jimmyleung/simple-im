package com.jimmy.simpleim.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int MAX_FRAME_LENGTH = Integer.MAX_VALUE;
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;


    public Spliter() {
        super(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
            ctx.channel().close();//不合符协议的请求直接关闭
            return null;
        }
        return super.decode(ctx, in);
    }
}
