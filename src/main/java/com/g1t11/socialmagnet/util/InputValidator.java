package com.g1t11.socialmagnet.util;

public class InputValidator {
    public static boolean isAlphanumeric(String input) {
        return input != null && input.matches("^[a-zA-Z0-9]+$");
    }
}
