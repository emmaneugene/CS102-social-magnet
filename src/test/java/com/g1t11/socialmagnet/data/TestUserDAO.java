package com.g1t11.socialmagnet.data;

import java.util.List;

import com.g1t11.socialmagnet.model.social.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestUserDAO {
    private UserDAO userDAO;

    @Before
    public void initDAO() {
        Database db = new Database();
        userDAO = new UserDAO(db.connection());
    }

    @Test
    public void testGetUser() {
        User expectedUser = new User("adam", "Adam Levine");

        User user = userDAO.getUser("adam");

        Assert.assertNotNull(expectedUser);
        Assert.assertEquals(expectedUser, user);
    }

    @Test
    public void testGetFriends() {
        User friendOne    = new User("charlie", "Charlie Puth");
        User friendTwo    = new User("elijah",  "Elijah Wood");
        List<User> expected = List.of(friendOne, friendTwo);

        List<User> friends = userDAO.getFriends(new User("adam", "Adam Levine"));

        Assert.assertEquals(expected, friends);
    }
}
