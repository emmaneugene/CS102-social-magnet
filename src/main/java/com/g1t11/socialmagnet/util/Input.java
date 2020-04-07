package com.g1t11.socialmagnet.util;

import java.io.Console;
import java.util.Scanner;

import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * A wrapper around <code>Scanner</code> that presents a prompt first.
 */
public class Input {
    private final Console secure_sc = System.console();

    private final Scanner sc = new Scanner(System.in);

    public String readPassword() {
        if (secure_sc == null) {
            return nextLine();
        }
        char[] pwd = secure_sc.readPassword();
        return String.copyValueOf(pwd);
    }

    /**
     * Get user input and display it as colored text.
     * @param color The color to display the input in.
     * @return The user input.
     */
    public String nextLine() {
        String input = sc.nextLine();
        clearColor();
        return input;
    }

    private void clearColor() {
        System.out.print(Color.RESET);
    }
}
