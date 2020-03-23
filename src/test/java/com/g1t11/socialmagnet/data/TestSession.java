package com.g1t11.socialmagnet.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestSession {
    private Session session;

    @Before
    public void initSession() {
        Database db = new Database();
        session = new Session(db);
    }

    @Test
    public void testCheckCredentialsValid() {
        boolean loginSuccessful = session.credentialsValid("adam", "maroon5");

        Assert.assertTrue(loginSuccessful);
    }

    @Test
    public void testCheckCredentialsInvalid() {
        boolean loginSuccessful = session.credentialsValid("adam", "solocareer");

        Assert.assertFalse(loginSuccessful);
    }

    @Test
    public void testUserExists() {
        boolean userExists = session.userExists("adam");

        Assert.assertTrue(userExists);
    }

    @Test
    public void testUserDoesNotExist() {
        boolean userExists = session.userExists("audrey");

        Assert.assertFalse(userExists);
    }

    @Test
    public void testAddUser() {
        boolean registered = session.addUser("gary", "Gary Oldman", "dracula123");

        Assert.assertTrue(registered);
        Assert.assertTrue(session.userExists("gary"));
    }

    @Test
    public void testAddExistingUser() {
        boolean registered = session.addUser("adam", "Adam Sandler", "uncutgems");

        Assert.assertFalse(registered);
    }
}
