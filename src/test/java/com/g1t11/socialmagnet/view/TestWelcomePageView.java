package com.g1t11.socialmagnet.view;

import com.g1t11.socialmagnet.TestApp;
import com.g1t11.socialmagnet.view.kit.TextView;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class TestWelcomePageView extends TestApp {
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

        view.render();

        String expected = "\033[H\033[2J";
        expected += String.join(System.lineSeparator(),
            "== Social Magnet :: Welcome ==",
            "Good afternoon, anonymous!",
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

        view.render();

        String expected = "\033[H\033[2J";
        expected += String.join(System.lineSeparator(),
            "== Social Magnet :: Welcome ==",
            "Good evening, anonymous!",
            "1. Register",
            "2. Login",
            "3. Exit",
            ""
        );

        Assert.assertEquals(expected, systemOutRule.getLog());
    }

    @Test
    public void testRenderWithStatus() {
        view = new WelcomePageView(20);

        view.setStatus(new TextView("Please enter a choice between 1 & 3!"));

        view.render();

        String expected = "\033[H\033[2J";
        expected += String.join(System.lineSeparator(),
            "== Social Magnet :: Welcome ==",
            "Please enter a choice between 1 & 3!",
            "Good evening, anonymous!",
            "1. Register",
            "2. Login",
            "3. Exit",
            ""
        );

        Assert.assertEquals(expected, systemOutRule.getLog());
    }
}
