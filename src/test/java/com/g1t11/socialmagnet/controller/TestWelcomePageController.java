package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.TestApp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class TestWelcomePageController extends TestApp {
    @Rule
    public final TextFromStandardInputStream systemInMock
        = TextFromStandardInputStream.emptyStandardInputStream();
    @Rule
    public final SystemOutRule systemOutRule 
        = new SystemOutRule().mute().enableLog();

    @Before
    public void initController() {
        App.shared().getNavigation().setFirstController(new WelcomePageController());
    }

    @Test
    public void testGoToLogin() {
        systemInMock.provideLines("2");
        App.shared().getNavigation().getCurrentController().run();

        LoginPageController expected = new LoginPageController();

        Assert.assertEquals(expected, App.shared().getNavigation().getCurrentController());
    }

    @Test
    public void testGoToLoginAndBack() {
        systemInMock.provideLines("2");
        App.shared().getNavigation().getCurrentController().run();

        systemInMock.provideLines("adam", "solocareer");
        App.shared().getNavigation().getCurrentController().run();

        WelcomePageController expected = new WelcomePageController();

        Assert.assertEquals(expected, App.shared().getNavigation().getCurrentController());
    }

    @Test
    public void testInvalidInput() {
        systemInMock.provideLines("pass");
        App.shared().getNavigation().getCurrentController().run();

        WelcomePageController expected = new WelcomePageController();
        expected.displayErrorMessage("Please enter a choice between 1 & 3!");

        Assert.assertEquals(expected, App.shared().getNavigation().getCurrentController());
    }

    @Test
    public void simulateExit() {
        systemInMock.provideLines("3");
        App.shared().getNavigation().getCurrentController().run();

        String expectedMessage = "Enter your choice > Goodbye!";
        String[] lines = systemOutRule.getLog().split("\n");
        Assert.assertEquals(expectedMessage, lines[lines.length - 1]);
    }
}
