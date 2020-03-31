package com.g1t11.socialmagnet.util;

import java.io.Console;
import java.util.Scanner;

/**
 * A wrapper around <code>Scanner</code> that presents a prompt first.
 */
public class PromptInput {
    // private final Console secure_sc = null;
    private final Console secure_sc = System.console();
    private final Scanner sc = new Scanner(System.in);

    private String prompt;

    private static final String promptIndicator = ">";

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

    /**
     * Sets the prompt text.
     * @param text The text to prompt.
     */
    public void setPrompt(String text) {
        prompt = text;
    }

    public String readPassword() {
        if (secure_sc == null) {
            return nextLine();
        }
        char[] pwd = secure_sc.readPassword(String.format("%s %s ", prompt, promptIndicator));
        return String.copyValueOf(pwd);
    }

    public String nextLine() {
        printPrompt();
        String input = sc.nextLine();
        clearColor();
        return input;
    }

    public int nextInt() {
        printPrompt();
        int input = sc.nextInt();
        clearColor();
        return input;
    }

    public double nextDouble() {
        printPrompt();
        double input = sc.nextDouble();
        clearColor();
        return input;
    }

    private void printPrompt() {
        System.out.printf("%s %s %s", prompt, promptIndicator, Painter.Color.YELLOW.code);
    }

    private void clearColor() {
        System.out.print(Painter.Color.RESET.code);
    }
}
