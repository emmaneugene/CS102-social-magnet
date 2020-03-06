package com.g1t11.socialmagnet.view;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class TestMainMenuView {
    @Rule
    public final TextFromStandardInputStream systemInMock
        = TextFromStandardInputStream.emptyStandardInputStream();
    @Rule
    public final SystemOutRule systemOutRule
        = new SystemOutRule().mute().enableLog();

    MainMenuView view = null;

    @Test
    public void testRender() {
        view = new MainMenuView();

        view.setFullname("Adam Levine");
        view.render();

        String expected = "\033[H\033[2J";
        expected += String.join(System.lineSeparator(),
            "== Social Magnet :: Main Menu ==",
            "Welcome, Adam Levine!",
            "1. News Feed",
            "2. My Wall",
            "3. My Friends",
            "4. City Farmers",
            "5. Logout",
            ""
        );

        Assert.assertEquals(expected, systemOutRule.getLog());
    }
}