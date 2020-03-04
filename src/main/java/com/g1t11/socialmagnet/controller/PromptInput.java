package com.g1t11.socialmagnet.controller;

import java.io.InputStream;
import java.util.Scanner;

public class PromptInput {
    private Scanner sc = new Scanner(System.in);

    private String prompt;

    private final String promptIndicator = ">";

    public PromptInput(String prompt) {
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