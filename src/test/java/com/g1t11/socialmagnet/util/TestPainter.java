package com.g1t11.socialmagnet.util;

import com.g1t11.socialmagnet.util.Painter.Color;

import org.junit.Assert;
import org.junit.Test;

public class TestPainter {
    @Test
    public void testPainterWithFormat() {
        String expected = "\u001b[31mred\u001b[32mgreen\u001b[34mblue\u001b[0m";
        
        String painted = Painter.paint("%rred%rgreen%rblue", Color.RED, Color.GREEN, Color.BLUE);

        Assert.assertEquals(expected, painted);
    }

    @Test
    public void testPainterWithStringFormat() {
        String expected = "\u001b[31mred%.2f \u001b[34m%s\u001b[0m";

        String painted = Painter.paint("%rred%.2f %r%s", Color.RED, Color.BLUE);

        Assert.assertEquals(expected, painted);
    }
}