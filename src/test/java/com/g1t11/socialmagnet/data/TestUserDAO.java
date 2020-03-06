package com.g1t11.socialmagnet.data;

import java.util.List;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.TestApp;
import com.g1t11.socialmagnet.model.social.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestUserDAO extends TestApp {
    private UserDAO userDAO;

    @Before
    public void initDAO() {
        userDAO = new UserDAO(App.shared().database().connection());
    }

    @Test
    public void testGetUserAndFriends() {
        User expectedUser = new User("adam",    "Adam Levine");
        User friendOne    = new User("charlie", "Charlie Puth");
        User friendTwo    = new User("elijah",  "Elijah Wood");
        expectedUser.setFriends(List.of(friendOne, friendTwo));

        User user = userDAO.getUser("adam");

        Assert.assertNotNull(expectedUser);
        Assert.assertEquals(expectedUser, user);
    }

    @Test
    public void testCheckCredentialsValid() {
        boolean loginSuccessful = userDAO.credentialsValid("adam", "maroon5");

        Assert.assertTrue(loginSuccessful);
    }

    @Test
    public void testCheckCredentialsInvalid() {
        boolean loginSuccessful = userDAO.credentialsValid("adam", "solocareer");

        Assert.assertFalse(loginSuccessful);
    }

    @Test
    public void testUserExists() {
        boolean userExists = userDAO.userExists("adam");

        Assert.assertTrue(userExists);
    }

    @Test
    public void testUserDoesNotExist() {
        boolean userExists = userDAO.userExists("audrey");

        Assert.assertFalse(userExists);
    }

    @Test
    public void testAddUser() {
        boolean registered = userDAO.addUser("gary", "Gary Oldman", "dracula123");

        Assert.assertTrue(registered);
        Assert.assertTrue(userDAO.userExists("gary"));
    }

    @Test
    public void testAddExistingUser() {
        boolean registered = userDAO.addUser("adam", "Adam Sandler", "uncutgems");

        Assert.assertFalse(registered);
    }
}
