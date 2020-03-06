package com.g1t11.socialmagnet.data;

import com.g1t11.socialmagnet.TestApp;

import org.junit.Assert;
import org.junit.Test;

public class TestSession extends TestApp {
    @Test
    public void testCheckCredentialsValid() {
        boolean loginSuccessful = app.session.credentialsValid("adam", "maroon5");

        Assert.assertTrue(loginSuccessful);
    }

    @Test
    public void testCheckCredentialsInvalid() {
        boolean loginSuccessful = app.session.credentialsValid("adam", "solocareer");

        Assert.assertFalse(loginSuccessful);
    }

    @Test
    public void testUserExists() {
        boolean userExists = app.session.userExists("adam");

        Assert.assertTrue(userExists);
    }

    @Test
    public void testUserDoesNotExist() {
        boolean userExists = app.session.userExists("audrey");

        Assert.assertFalse(userExists);
    }

    @Test
    public void testAddUser() {
        boolean registered = app.session.addUser("gary", "Gary Oldman", "dracula123");

        Assert.assertTrue(registered);
        Assert.assertTrue(app.session.userExists("gary"));
    }

    @Test
    public void testAddExistingUser() {
        boolean registered = app.session.addUser("adam", "Adam Sandler", "uncutgems");

        Assert.assertFalse(registered);
    }
}
