package com.g1t11.socialmagnet.model.social;

import java.util.List;

import com.g1t11.socialmagnet.util.Painter;

import org.junit.Assert;
import org.junit.Test;

public class TestThread {
    @Test
    public void testFormatContent() {
        Thread thread = new Thread(
            7, 
            "elijah", 
            "elijah",
            "Had a great night with @adam, @britney, and @charlie",
            4
        );
        thread.formatContentTags(List.of("adam", "britney"));

        String expected = Painter.paintf(
            "Had a great night with [{adam}], [{britney}], and @charlie",
            Painter.Color.BLUE
        );
        Assert.assertEquals(expected, thread.getContent());
    }
}
