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
        String expectedNestedColors = "[\u001b[31mred\u001b[32mgreen\u001b[0m\u001b[31mstill red\u001b[0m\u001b[34mblue\u001b[0m]";
        String nestedColors = Painter.paintf("[[{red[{green}]still red}][{blue}]]", Color.RED, Color.GREEN, Color.BLUE);
        Assert.assertEquals(expectedNestedColors, nestedColors);

        String expectedNestedColorsWithinBold = "\u001b[32m\u001b[1mbolded and green\u001b[0m\u001b[34m\u001b[1mbolded and blue\u001b[0m\u001b[0m\u001b[31msomething red\u001b[0m";
        String nestedColorsWithinBold = Painter.paintf("[{[{bolded and green}][{bolded and blue}]}][{something red}]", Color.BOLD, Color.GREEN, Color.BLUE, Color.RED);
        Assert.assertEquals(expectedNestedColorsWithinBold, nestedColorsWithinBold);
    }

    @Test
    public void testPaintfWithStringFormat() {
        String expected = "\u001b[31mred%.2f\u001b[0m \u001b[34m%s\u001b[0m";

        String painted = Painter.paintf("[{red%.2f}] [{%s}]", Color.RED, Color.BLUE);

        Assert.assertEquals(expected, painted);
    }

    @Test
    public void testPaintWithUnderloadedColor() {
        String expected = "\u001b[31mred\u001b[0m \u001b[34mblue\u001b[0m out of colors but \u001b[34mno\u001b[0m";

        String painted = Painter.paintf("[{red}] [{blue}] out of colors but [{no}]", Color.RED, Color.BLUE);

        Assert.assertEquals(expected, painted);
    }
}
