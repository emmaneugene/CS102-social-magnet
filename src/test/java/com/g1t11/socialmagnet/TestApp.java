package com.g1t11.socialmagnet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestApp {
    private final InputStream stdin = System.in;
    private final PrintStream stdout = System.out;

    private static ByteArrayInputStream input;
    private static ByteArrayOutputStream output;

    @BeforeAll
    public static void initApplication() {
        App.shared().setAppName("Social Magnet");
    }

    @BeforeEach
    public void setOutputStreams() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }
    
    @AfterEach
    public void restoreOutputStreams() {
        System.setIn(stdin);
        System.setOut(stdout);
    }

    /**
     * Prepare input before calling .next() on the Scanner.
     * If we need to prepare multiple lines of input, separate each entry with
     * a line separator.
     * @param data The simulated input
     */
    public static void provideInput(String data) {
        input = new ByteArrayInputStream(data.getBytes());
        System.setIn(input);
    }

    public static String getOutput() {
        return output.toString();
    }
}
