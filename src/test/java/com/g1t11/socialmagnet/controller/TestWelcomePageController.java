package com.g1t11.socialmagnet.controller;
import com.g1t11.socialmagnet.TestApp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;


public class TestWelcomePageController extends TestApp {
    @Rule
    public final TextFromStandardInputStream systemInMock
        = TextFromStandardInputStream.emptyStandardInputStream();
    @Rule
    public final SystemOutRule systemOutRule 
        = new SystemOutRule().mute().enableLog();
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void initController() {
        app.nav.setFirstController(new WelcomePageController(app.db.connection()));
    }

    @Test
    public void testGoToLogin() {
        systemInMock.provideLines("2");
        app.nav.currentController().run();

        Assert.assertTrue(app.nav.currentController() instanceof LoginPageController);
    }

    @Test
    public void testGoToLoginAndBack() {
        systemInMock.provideLines("2");
        app.nav.currentController().run();

        Assert.assertTrue(app.nav.currentController() instanceof LoginPageController);

        systemInMock.provideLines("adam", "solocareer");
        app.nav.currentController().run();

        Assert.assertTrue(app.nav.currentController() instanceof WelcomePageController);
    }

    @Test
    public void testInvalidInput() {
        systemInMock.provideLines("pass");
        app.nav.currentController().run();

        Assert.assertTrue(app.nav.currentController() instanceof WelcomePageController);
    }

    @Test
    public void simulateExit() {
        exit.expectSystemExit();
        systemInMock.provideLines("3");
        app.nav.currentController().run();

        String expectedMessage = "Enter your choice > Goodbye!";
        String[] lines = systemOutRule.getLog().split(System.lineSeparator());
        Assert.assertEquals(expectedMessage, lines[lines.length - 1]);
    }
}
