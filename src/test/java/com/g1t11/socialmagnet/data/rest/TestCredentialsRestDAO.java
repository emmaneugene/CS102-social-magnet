package com.g1t11.socialmagnet.data.rest;

import com.g1t11.socialmagnet.data.DatabaseException;
import com.g1t11.socialmagnet.data.DatabaseException.SQLErrorCode;
import com.g1t11.socialmagnet.model.social.User;

import org.junit.Assert;
import org.junit.Test;

public class TestCredentialsRestDAO {
    private CredentialsRestDAO credDAO;

    public static final User adam = new User("adam", "Adam Levine");

    public TestCredentialsRestDAO() {
        credDAO = new CredentialsRestDAO();
    }

    @Test
    public void testLogin() {
        User expected = adam;
        User actual = credDAO.login("adam", "maroon5");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testLoginFail() {
        User actual = credDAO.login("ADAM", "maroon5");
        Assert.assertNull(actual);
    }

    @Test
    public void testRegister() {
        credDAO.register("zulu", "Zulu Wulu", "xyz");

        Assert.assertTrue(credDAO.userExists("zulu"));
    }

    @Test
    public void testRegisterExisting() {
        try {
            credDAO.register("adam", "Adam Sandler", "gems");
            Assert.assertTrue(false);
        } catch (DatabaseException e) {
            Assert.assertTrue(
                    SQLErrorCode.REGISTER_EXISTING_USER.value == e.getCode());
        }
    }

    @Test
    public void testUserExists() {
        boolean userExists = credDAO.userExists("adam");

        Assert.assertTrue(userExists);
    }

    @Test
    public void testUserDoesNotExist() {
        boolean userExists = credDAO.userExists("audrey");

        Assert.assertFalse(userExists);
    }
}