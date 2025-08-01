package com.example.schedulemanagementapi.util;

public class StringExtension {

    public static boolean isLengthBetween(String str, int min, int max) {
        return min <= str.length() && str.length() <= max;
    }

}
