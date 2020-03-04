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
        boolean loginSuccessful = userDAO.checkCredentials("adam", "maroon5");

        Assert.assertTrue(loginSuccessful);
    }

    @Test
    public void testCheckCredentialsInvalid() {
        boolean loginSuccessful = userDAO.checkCredentials("adam", "solocareer");

        Assert.assertFalse(loginSuccessful);
    }
}
