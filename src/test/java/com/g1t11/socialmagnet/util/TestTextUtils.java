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
    public void testPrettyNumberThousand() {
        String expected = "1,000";
        String actual = TextUtils.prettyNumber(1000);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPrettyNumberThreeDigits() {
        String expected = "123";
        String actual = TextUtils.prettyNumber(123);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPrettyNumberTwoDigits() {
        String expected = "12";
        String actual = TextUtils.prettyNumber(12);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPrettyNumberOneDigit() {
        String expected = "1";
        String actual = TextUtils.prettyNumber(1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPrettyNumberNegative() {
        String expected = "-12,345";
        String actual = TextUtils.prettyNumber(-12345);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPrettyListOne() {
        String expected = "1";
        String actual = TextUtils.prettyList(new String[]{"1"});
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPrettyListTwo() {
        String expected = "1 and 2";
        String actual = TextUtils.prettyList(new String[]{"1", "2"});
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPrettyListThree() {
        String expected = "1, 2, and 3";
        String actual = TextUtils.prettyList(new String[]{"1", "2", "3"});
        Assert.assertEquals(expected, actual);
    }
}
