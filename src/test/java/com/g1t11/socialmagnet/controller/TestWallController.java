package com.g1t11.socialmagnet.controller;
import com.g1t11.socialmagnet.TestApp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;


public class TestWallController extends TestApp {
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
        app.nav.setFirstController(new WallController(app.db.connection()));
        app.nav.session().login("adam", "maroon5");
    }

    @Test
    public void test() {
        systemInMock.provideLines("2");
        app.nav.currentController().run();

        MainMenuController expected = new MainMenuController(app.db.connection());
        expected.setNavigation(app.nav);
        expected.updateView();

        Assert.assertEquals(expected, app.nav.currentController());
    }
}
