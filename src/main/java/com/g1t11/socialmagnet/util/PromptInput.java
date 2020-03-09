package com.g1t11.socialmagnet.util;

import java.util.Scanner;

/**
 * A wrapper around <code>Scanner</code> that presents a prompt first.
 */
public class PromptInput {
    private final Scanner sc = new Scanner(System.in);

    private String prompt;

    private final String promptIndicator = ">";

    public PromptInput(String prompt) {
        this.prompt = prompt;
    }

    public PromptInput() {
        this("Enter your choice");
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
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
