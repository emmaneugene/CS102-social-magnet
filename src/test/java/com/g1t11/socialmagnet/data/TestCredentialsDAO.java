package com.g1t11.socialmagnet.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestCredentialsDAO {
    private CredentialsDAO session;

    @Before
    public void initSession() {
        Database db = new Database();
        session = new CredentialsDAO(db);
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
}
