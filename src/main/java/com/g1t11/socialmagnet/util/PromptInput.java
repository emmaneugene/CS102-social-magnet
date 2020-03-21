package com.g1t11.socialmagnet.util;

import java.io.Console;
import java.util.Scanner;

/**
 * A wrapper around <code>Scanner</code> that presents a prompt first.
 */
public class PromptInput {
    // private final Console secure_sc = System.console();
    private final Console secure_sc = null;
    private final Scanner sc = new Scanner(System.in);

    private String prompt;

    private final String promptIndicator = ">";

    public PromptInput(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Constructs a default prompt that displays:
     * <p>
     * <code>Enter your choice</code>
     */
    public PromptInput() {
        this("Enter your choice");
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String readPassword() {
        if (secure_sc == null) {
            return nextLine();
        }
        char[] pwd = secure_sc.readPassword(String.format("%s %s ", prompt, promptIndicator));
        return String.copyValueOf(pwd);
    }

    public String nextLine() {
        System.out.printf("%s %s ", prompt, promptIndicator);
        return sc.nextLine();
    }

    public int nextInt() {
        System.out.printf("%s %s ", prompt, promptIndicator);
        return sc.nextInt();
    }

    public double nextDouble() {
        System.out.printf("%s %s ", prompt, promptIndicator);
        return sc.nextDouble();
    }
}
