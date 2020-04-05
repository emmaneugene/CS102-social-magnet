package com.g1t11.socialmagnet.util;

/**
 * This class consists exclusively of static methods that validate inputs.
 */
public class InputValidator {
    /**
     * This method is used to check if the input string is alphanumeric.
     * @param input The input string used for validation.
     * @return If input is alphanumeric, it will return true, else return false.
     */
    public static boolean isAlphanumeric(String input) {
        return input != null && input.matches("^[a-zA-Z0-9]+$");
    }
}
