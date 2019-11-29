package com.jimmy.simpleim.protocol.response;

import com.jimmy.simpleim.protocol.Command;
import com.jimmy.simpleim.protocol.Packet;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {
    private String userId;
    private String userName;
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
