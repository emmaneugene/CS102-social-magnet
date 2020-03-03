package com.g1t11.socialmagnet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {
    @BeforeAll
    public static void initApplication() {
        App.shared().setAppName("Social Magnet");
    }

    private final InputStream stdin = System.in;
    private final PrintStream stdout = System.out;

    private static ByteArrayInputStream input;
    private static ByteArrayOutputStream output;

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

    public static void provideInput(String data) {
        input = new ByteArrayInputStream(data.getBytes());
        System.setIn(input);
    }

    public static String getOutput() {
        return output.toString();
    }
}
