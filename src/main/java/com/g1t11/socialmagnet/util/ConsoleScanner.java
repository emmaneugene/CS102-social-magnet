package com.g1t11.socialmagnet.util;

import java.util.Scanner;

public class ConsoleScanner {
    private static ConsoleScanner sharedInstance = null;

    private Scanner console = new Scanner(System.in);

    private ConsoleScanner() {}

    public static ConsoleScanner shared() {
        if (sharedInstance == null)
            sharedInstance = new ConsoleScanner();
        return sharedInstance;
    }

    public String getInput() {
        return console.next();
    }
}
