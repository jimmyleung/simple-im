package com.jimmy.simpleim.protocol.response;

import com.jimmy.simpleim.protocol.Command;
import com.jimmy.simpleim.protocol.Packet;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_RESPONSE;
    }
}
