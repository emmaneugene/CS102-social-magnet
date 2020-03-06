package com.g1t11.socialmagnet.view;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class TestWelcomePageView {
    @Rule
    public final TextFromStandardInputStream systemInMock
        = TextFromStandardInputStream.emptyStandardInputStream();
    @Rule
    public final SystemOutRule systemOutRule
        = new SystemOutRule().mute().enableLog();

    WelcomePageView view = null;

    @Test
    public void testRenderMorning() {
        view = new WelcomePageView(8);

        view.setUsername("anonymous");
        view.render();

        String expected = "\033[H\033[2J";
        expected += String.join(System.lineSeparator(),
            "== Social Magnet :: Welcome ==",
            "Good morning, anonymous!",
            "1. Register",
            "2. Login",
            "3. Exit",
            ""
        );

        Assert.assertEquals(expected, systemOutRule.getLog());
    }

    @Test
    public void testRenderAfternoon() {
        view = new WelcomePageView(13);

        view.setUsername("adam");
        view.render();

        String expected = "\033[H\033[2J";
        expected += String.join(System.lineSeparator(),
            "== Social Magnet :: Welcome ==",
            "Good afternoon, adam!",
            "1. Register",
            "2. Login",
            "3. Exit",
            ""
        );

        Assert.assertEquals(expected, systemOutRule.getLog());
    }

    @Test
    public void testRenderEvening() {
        view = new WelcomePageView(20);

        view.setUsername("elijah");
        view.render();

        String expected = "\033[H\033[2J";
        expected += String.join(System.lineSeparator(),
            "== Social Magnet :: Welcome ==",
            "Good evening, elijah!",
            "1. Register",
            "2. Login",
            "3. Exit",
            ""
        );

        Assert.assertEquals(expected, systemOutRule.getLog());
    }
}
