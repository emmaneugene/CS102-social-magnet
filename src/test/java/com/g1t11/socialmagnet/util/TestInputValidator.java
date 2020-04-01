package com.g1t11.socialmagnet.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestInputValidator {
    @Test
    public void testIsAlphanumeric() {
        Assert.assertTrue(InputValidator.isAlphanumeric("garyOldman123"));
    }

    @Test
    public void testIsAlphanumericInvalidChars() {
        List<Character> invalidChars
                = List.of('_', '-', ' ', '.', '*', '+');
        for (char c : invalidChars) {
            Assert.assertFalse(
                    InputValidator.isAlphanumeric("gary" + c + "oldman"));
        }
    }

    @Test
    public void testIsAlphanumericBadStrings() {
        Assert.assertFalse(InputValidator.isAlphanumeric(""));
        Assert.assertFalse(InputValidator.isAlphanumeric(null));
    }
}
