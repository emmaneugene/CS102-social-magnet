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

    @Test
    public void testNotEquals() {
        User user1 = new User("adam", "Adam Levine");
        User user2 = new User("adam", "Adam Sandler");
        User user3 = new User("levine", "Adam Levine");
        Assert.assertNotEquals(user1, user2);
        Assert.assertNotEquals(user1, user3);
    }
}
