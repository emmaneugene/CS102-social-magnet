package com.g1t11.socialmagnet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import com.g1t11.socialmagnet.controller.WelcomePageController;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestWelcomePage {
    private final InputStream stdin = System.in;
    private final PrintStream stdout = System.out;

    private ByteArrayInputStream input;
    private ByteArrayOutputStream output;

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

    private void provideInput(String data) {
        input = new ByteArrayInputStream(data.getBytes());
        System.setIn(input);
    }

    private String getOutput() {
        return output.toString();
    }

    @Test
    @Order(1)
    public void displayMenu() {
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
        assertEquals(expected, getOutput());
    }

    @Test
    @Order(2)
    public void simulateInvalidInput() {
        App.shared().setAppName("Social Magnet");
        App.shared().setFirstController(new WelcomePageController());
        provideInput("5");
        App.shared().getCurrentController().handleInput();
        App.shared().getCurrentController().updateView();
        String expectedError = "Please enter a choice between 1 & 3!";
        String errorMessage = output.toString().split("\\r?\\n")[1];
        assertEquals(expectedError, errorMessage);
    }
}
