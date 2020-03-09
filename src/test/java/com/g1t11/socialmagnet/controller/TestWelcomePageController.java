package com.g1t11.socialmagnet.controller;
import com.g1t11.socialmagnet.TestApp;
import com.g1t11.socialmagnet.view.kit.TextView;

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
        app.nav.setFirstController(new WelcomePageController());
    }

    @Test
    public void testGoToLogin() {
        systemInMock.provideLines("2");
        app.nav.currentController().run();

        LoginPageController expected = new LoginPageController();

        Assert.assertEquals(expected, app.nav.currentController());
    }

    @Test
    public void testGoToLoginAndBack() {
        systemInMock.provideLines("2");
        app.nav.currentController().run();

        systemInMock.provideLines("adam", "solocareer");
        app.nav.currentController().run();

        WelcomePageController expected = new WelcomePageController();
        expected.setNavigation(app.nav);
        expected.updateView();

        Assert.assertEquals(expected, app.nav.currentController());
    }

    @Test
    public void testInvalidInput() {
        systemInMock.provideLines("pass");
        app.nav.currentController().run();

        WelcomePageController expected = new WelcomePageController();
        expected.setNavigation(app.nav);
        expected.updateView();
        expected.getView().setStatus("Please enter a choice between 1 & 3!", TextView.Color.RED);

        Assert.assertEquals(expected, app.nav.currentController());
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
