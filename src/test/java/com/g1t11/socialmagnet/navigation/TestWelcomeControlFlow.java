package com.g1t11.socialmagnet.navigation;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.TestApp;
import com.g1t11.socialmagnet.controller.WelcomePageController;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

public class TestWelcomeControlFlow extends TestApp {
    @BeforeAll
    public static void initializeWelcome() {
        App.shared().getNavigation().setFirstController(new WelcomePageController());
    }

    @Test
    public void simulateInvalidInput() {
        provideInput("5");
        App.shared().getNavigation().getCurrentController().handleInput();
        App.shared().getNavigation().getCurrentController().updateView();

        String expectedError = "Please enter a choice between 1 & 3!";
        String errorMessage = getOutput().split(System.lineSeparator())[1];
        Assertions.assertEquals(expectedError, errorMessage);
    }

    @Test
    public void simulateExit() {
        provideInput("3");
        App.shared().getNavigation().getCurrentController().handleInput();

        String expectedMessage = "Goodbye!\n";
        String message = getOutput();
        Assertions.assertEquals(expectedMessage, message);
    }
}
