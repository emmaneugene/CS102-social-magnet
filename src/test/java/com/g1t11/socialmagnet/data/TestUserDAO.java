package com.g1t11.socialmagnet.data;

import java.util.List;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.TestApp;
import com.g1t11.socialmagnet.model.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUserDAO extends TestApp {
    private UserDAO userDAO;

    @BeforeEach
    public void initDAO() {
        userDAO = new UserDAO(App.shared().getDatabase().connection());
    }

    @Test
    public void testGetUserAndFriends() {
        User expectedUser = new User("adam",    "Adam Levine");
        User friendOne    = new User("charlie", "Charlie Puth");
        User friendTwo    = new User("elijah",  "Elijah Wood");
        expectedUser.setFriends(List.of(friendOne, friendTwo));

        User user = userDAO.getUser("adam");

        Assertions.assertNotNull(expectedUser);
        Assertions.assertEquals(expectedUser, user);
    }

    @Test
    public void testCheckCredentialsValid() {
        boolean loginSuccessful = userDAO.checkCredentials("adam", "maroon5");

        Assertions.assertTrue(loginSuccessful);
    }

    @Test
    public void testCheckCredentialsInvalid() {
        boolean loginSuccessful = userDAO.checkCredentials("adam", "solocareer");

        Assertions.assertFalse(loginSuccessful);
    }
}
