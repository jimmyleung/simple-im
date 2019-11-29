package com.jimmy.simpleim.protocol.request;

import com.jimmy.simpleim.protocol.Command;
import com.jimmy.simpleim.protocol.Packet;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {

    private String userName;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
