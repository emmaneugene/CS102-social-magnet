package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.TestApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestWelcomePageController extends TestApp {
    @BeforeEach
    public void initController() {
        App.shared().getNavigation().setFirstController(new WelcomePageController());
    }

    @Test
    public void testGoToLogin() {
        provideInput("2");
        App.shared().getNavigation().getCurrentController().run();

        LoginPageController expected = new LoginPageController();

        Assertions.assertEquals(expected, App.shared().getNavigation().getCurrentController());
    }

    @Test
    public void testGoToLoginAndBack() {
        provideInput("2");
        App.shared().getNavigation().getCurrentController().run();
        provideInput("y");
        App.shared().getNavigation().getCurrentController().run();

        WelcomePageController expected = new WelcomePageController();

        Assertions.assertEquals(expected, App.shared().getNavigation().getCurrentController());
    }

    @Test
    public void testInvalidInput() {
        provideInput("pass");
        App.shared().getNavigation().getCurrentController().run();

        WelcomePageController expected = new WelcomePageController();
        expected.displayErrorMessage("Please enter a choice between 1 & 3!");

        Assertions.assertEquals(expected, App.shared().getNavigation().getCurrentController());
    }

    @Test
    public void simulateExit() {
        provideInput("3");
        App.shared().getNavigation().getCurrentController().run();

        String expectedMessage = "Enter your choice > Goodbye!";
        String[] lines = getOutput().split("\n");
        Assertions.assertEquals(expectedMessage, lines[lines.length - 1]);
    }
}
