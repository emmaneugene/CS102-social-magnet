package com.g1t11.socialmagnet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestLoginPageController {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final PrintStream stdout = System.out;

    @BeforeEach
    public void setOutputStreams() {
        System.setOut(new PrintStream(output));
    }
    
    @AfterEach
    public void restoreOutputStreams() {
        System.setOut(stdout);
    }
}
