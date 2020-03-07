package com.g1t11.socialmagnet.model.social;

import java.util.List;

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

    @Test
    public void testMultipleFriends() {
        User user1 = new User("adam",    "Adam Levine");
        User user1Friend1    = new User("charlie", "Charlie Puth");
        User user1Friend2    = new User("elijah",  "Elijah Wood");
        user1.setFriends(List.of(user1Friend1, user1Friend2));

        User user2 = new User("adam",    "Adam Levine");
        User user2Friend1    = new User("charlie", "Charlie Puth");
        User user2Friend2    = new User("elijah",  "Elijah Wood");
        user2.setFriends(List.of(user2Friend1, user2Friend2));

        Assert.assertEquals(user1, user2);
    }
}
