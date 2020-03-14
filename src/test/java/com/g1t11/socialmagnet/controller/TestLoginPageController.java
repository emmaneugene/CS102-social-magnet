package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.TestApp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class TestLoginPageController extends TestApp {
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
        app.nav.setFirstController(new LoginController(app.db.connection()));
    }

    @Test
    public void testGoToMainMenu() {
        systemInMock.provideLines("adam", "maroon5");
        app.nav.currentController().run();

        Assert.assertTrue(app.nav.currentController() instanceof MainMenuController);
    }
}
