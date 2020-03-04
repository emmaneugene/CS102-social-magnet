package com.g1t11.socialmagnet.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestUser {
    @Test
    public void testEquals() {
        User user1 = new User();
        User user2 = new User();
        Assertions.assertEquals(user1, user2);

        user1.setUsername("adam");
        user2.setUsername("adam");
        user1.setFullName("Adam Levine");
        user1.setFullName("Adam Levine");
    }
}
