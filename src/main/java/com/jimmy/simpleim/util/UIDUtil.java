package com.jimmy.simpleim.util;

import java.util.UUID;

public class UIDUtil {
    public static String generateId() {
        return UUID.randomUUID().toString().split(",")[0];
    }
}
