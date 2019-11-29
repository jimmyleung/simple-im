package com.jimmy.simpleim.util;

import io.netty.util.AttributeKey;

public interface SessionAttribute {
    AttributeKey<Session> session = AttributeKey.newInstance("session");
}
