package com.jimmy.simpleim.protocol;

public interface Command {

    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte HEART_BEAT_REQUEST = 3;

    Byte HEART_BEAT_RESPONSE = 4;
}
