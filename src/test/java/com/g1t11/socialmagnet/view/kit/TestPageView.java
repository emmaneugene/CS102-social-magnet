package com.g1t11.socialmagnet.view.kit;

import com.g1t11.socialmagnet.TestApp;

import com.g1t11.socialmagnet.view.kit.TextView;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class TestPageView extends TestApp {
    @Rule
    public final TextFromStandardInputStream systemInMock
        = TextFromStandardInputStream.emptyStandardInputStream();
    @Rule
    public final SystemOutRule systemOutRule 
        = new SystemOutRule().mute().enableLog();

    PageView view = null;

    @Test
    public void testRenderWithStatus() {
        view = new PageView("Testing Page");

        view.setStatus(new TextView("Example status message."));

        view.render();

        String expected = "\033[H\033[2J";
        expected += String.join(System.lineSeparator(),
            "== Social Magnet :: Testing Page ==",
            "Example status message.",
            ""
        );

        Assert.assertEquals(expected, systemOutRule.getLog());
    }
}
