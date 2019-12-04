package com.jimmy.simpleim.protocol.request;

import com.jimmy.simpleim.protocol.Command;
import com.jimmy.simpleim.protocol.Packet;

public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_REQUEST;
    }
}
