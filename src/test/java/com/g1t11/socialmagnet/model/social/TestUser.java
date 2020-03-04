package com.g1t11.socialmagnet.model.social;

import org.junit.Assert;
import org.junit.Test;

public class TestUser {
    @Test
    public void testEquals() {
        User user1 = new User("adam", "Adam Levine");
        User user2 = new User("adam", "Adam Levine");
        Assert.assertEquals(user1, user2);
    }
}
