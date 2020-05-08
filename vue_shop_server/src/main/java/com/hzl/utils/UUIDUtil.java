package com.hzl.utils;

import java.util.UUID;

public class UUIDUtil {
    private UUIDUtil() {
    }

    public static String createUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
