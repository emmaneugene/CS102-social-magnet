package com.g1t11.socialmagnet.util;

import org.junit.Assert;
import org.junit.Test;

public class TestTextUtils {
    @Test
    public void testCountedWordPlural() {
        String expected = "6 bags";
        String actual = TextUtils.countedWord(6, "bag", "bags");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCountedWordSingular() {
        String expected = "1 word";
        String actual = TextUtils.countedWord(1, "word", "words");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCountedWordZero() {
        String expected = "0 chances";
        String actual = TextUtils.countedWord(0, "chance", "chances");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPrettyNumber() {
        String expected = "1,234,567";
        String actual = TextUtils.prettyNumber(1234567);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPrettyNumberSmall() {
        String expected = "123";
        String actual = TextUtils.prettyNumber(123);
        Assert.assertEquals(expected, actual);
    }
}