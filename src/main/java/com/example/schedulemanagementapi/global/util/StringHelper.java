package com.example.schedulemanagementapi.global.util;

public class StringHelper {

    public static boolean isLengthBetween(String str, int min, int max) {
        return min <= str.length() && str.length() <= max;
    }

}
