package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.TestApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestWelcomePageController extends TestApp {
    @BeforeAll
    public static void initController() {
        App.shared().getNavigation().setFirstController(new WelcomePageController());
    }

    @Test
    public void simulateInvalidInput() {
        provideInput("5");
        App.shared().getNavigation().getCurrentController().run();

        WelcomePageController expected = new WelcomePageController();
        expected.displayErrorMessage("Please enter a choice between 1 & 3!");

        Assertions.assertEquals(expected, App.shared().getNavigation().getCurrentController());
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
