package com.g1t11.socialmagnet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.g1t11.socialmagnet.controller.WelcomePageController;

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

    @Test
    public void displayWelcomeMenu() {
        App.shared().setAppName("Social Magnet");
        App.shared().setFirstController(new WelcomePageController());
        App.shared().getCurrentController().updateView();

        String expected = "\033[H\033[2J";
        expected += String.join("\n",
            "== Social Magnet :: Welcome ==",
            "Good evening, anonymous!",
            "1. Register",
            "2. Login",
            "3. Exit",
            "Enter your choice > "
        );
        assertEquals(expected, output.toString());
    }
}
