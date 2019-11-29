package com.jimmy.simpleim.util;

import com.sun.deploy.security.SessionCertStore;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

    private static Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(SessionAttribute.session).set(session);
    }

    public static void unbindSesion(Channel channel) {
        if (islLogin(channel)) {
            Session session = getSession(channel);
            String userId = session.getUserId();
            userIdChannelMap.put(userId, null);
            channel.attr(SessionAttribute.session).set(null);
        }
    }

    public static boolean islLogin(Channel channel) {
        return getSession(channel) != null;
    }

    public static Session getSession(Channel channel) {
        return channel.attr(SessionAttribute.session).get();
    }
}
