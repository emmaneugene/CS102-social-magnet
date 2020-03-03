package com.g1t11.socialmagnet.util;

public class Greeting {
    public static String basedOnHour(int hour) {
        if (hour < 12) return "morning";
        if (hour < 19) return "afternoon";
        return "evening";
    }
}
