package com.g1t11.socialmagnet.util;

import java.io.Console;
import java.util.Scanner;

import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * A wrapper around <code>Scanner</code> that presents a prompt first.
 */
public class Input {
    // private final Console secure_sc = null;
    private final Console secure_sc = System.console();
    private final Scanner sc = new Scanner(System.in);

    /**
     * A method to read password from console instead of scanner. This will 
     * allow the masking of the password.
     * @return The password.
     */
    public String readPassword() {
        if (secure_sc == null) {
            return nextLine();
        }
        char[] pwd = secure_sc.readPassword();
        return String.copyValueOf(pwd);
    }

    /**
     * A method to read next line of input in scanner.
     * @return The content of next line.
     */
    public String nextLine() {
        String input = sc.nextLine();
        clearColor();
        return input;
    }

    /**
     * A method to clear the color.
     */
    private void clearColor() {
        System.out.print(Color.RESET);
    }
}
