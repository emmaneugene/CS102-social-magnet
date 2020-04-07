package com.g1t11.socialmagnet.util;

import java.io.Console;
import java.util.Scanner;

import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * A wrapper around <code>Scanner</code>.
 */
public class Input {
    private final Console secure_sc = System.console();

    private final Scanner sc = new Scanner(System.in);

    /**
     * A method to read sensitive passwords from console without using
     * {@link Scanner}. This allows reading through a secure console system.
     * <p>
     * However, {@link Console} does not exist in some environments, in which
     * case the application falls back on the Scanner.
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
     * A method to read the next line of input through {@link Scanner}
     * @return The content of the next line.
     */
    public String nextLine() {
        String input = sc.nextLine();
        clearColor();
        return input;
    }

    /**
     * A method to clear any color set on the input before rendering new UI.
     * <p>
     * Input color is determined by a page view. However, the page view has no
     * access to the console after handling input. Therefore, the color must be
     * reset after input is returned.
     */
    private void clearColor() {
        System.out.print(Color.RESET);
    }
}
