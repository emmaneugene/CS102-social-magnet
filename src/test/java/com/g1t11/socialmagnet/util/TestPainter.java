package com.g1t11.socialmagnet.util;

import com.g1t11.socialmagnet.util.Painter.Color;

import org.junit.Assert;
import org.junit.Test;

public class TestPainter {
    @Test
    public void testPaint() {
        String expected = "\u001b[32mHello!\u001b[0m";
        
        String painted = Painter.paint("Hello!", Color.GREEN);

        Assert.assertEquals(expected, painted);
    }

    @Test
    public void testPaintf() {
        String expected = "\u001b[31mred\u001b[0m\u001b[32mgreen\u001b[0m\u001b[34mblue\u001b[0m";
        
        String painted = Painter.paintf("{{red}}{{green}}{{blue}}", Color.RED, Color.GREEN, Color.BLUE);

        Assert.assertEquals(expected, painted);
    }

    @Test
    public void testPaintfWithStringFormat() {
        String expected = "\u001b[31mred%.2f\u001b[0m \u001b[34m%s\u001b[0m";

        String painted = Painter.paintf("{{red%.2f}} {{%s}}", Color.RED, Color.BLUE);

        Assert.assertEquals(expected, painted);
    }
}