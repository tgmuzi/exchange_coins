package com.zeus.utils;

import java.nio.charset.StandardCharsets;
import org.springframework.util.StringUtils;

public class HexUtils {
    public static String encode(String input) {
        if (StringUtils.isEmpty(input)) {
            return "";
        }
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static String decode(String input) {
        if (StringUtils.isEmpty(input)) {
            return "";
        }
        byte[] bytes = new byte[input.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(input.substring(i * 2, i * 2 + 2), 16);
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
