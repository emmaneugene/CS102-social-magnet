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
    public void simulateLogin() {
        provideInput("2");
        App.shared().getNavigation().getCurrentController().run();

        LoginPageController expected = new LoginPageController();
        expected.updateView();

        Assertions.assertEquals(expected, App.shared().getNavigation().getCurrentController());
    }

    @Test
    public void simulateLoginAndBack() {
        provideInput("2");
        App.shared().getNavigation().getCurrentController().run();

        App.shared().getNavigation().pop();

        WelcomePageController expected = new WelcomePageController();
        expected.updateView();

        Assertions.assertEquals(expected, App.shared().getNavigation().getCurrentController());
    }

    @Test
    public void simulateInvalidInput() {
        provideInput("5");
        App.shared().getNavigation().getCurrentController().run();

        WelcomePageController expected = new WelcomePageController();
        expected.updateView();
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
