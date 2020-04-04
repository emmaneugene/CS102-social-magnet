package com.g1t11.socialmagnet.data;

import java.util.List;

import com.g1t11.socialmagnet.data.DatabaseException.SQLErrorCode;
import com.g1t11.socialmagnet.model.social.User;

import org.junit.Assert;
import org.junit.Test;

public class TestUserRestDAO {
    UserRestDAO userDAO;

    public static final User adam = new User("adam", "Adam Levine");
    public static final User britney = new User("britney", "Britney Spears");
    public static final User charlie = new User("charlie", "Charlie Puth");
    public static final User danny   = new User("danny", "Danny DeVito");
    public static final User elijah  = new User("elijah", "Elijah Wood");
    public static final User frank   = new User("frank", "Frank Sinatra");
    public static final User gary    = new User("gary", "Gary Oldman");
    public static final User howard  = new User("howard", "Howard Duck");
    public static final User icarus  = new User("icarus", "Icarus");

    public TestUserRestDAO() {
        userDAO = new UserRestDAO();
    }

    @Test
    public void testGetUser() {
        User expected = adam;

        User actual = userDAO.getUser("adam");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetNonExistentUser() {
        try {
            userDAO.getUser("ADAM");
            Assert.assertTrue(false);
        } catch (DatabaseException e) {
            Assert.assertEquals(
                    SQLErrorCode.USER_NOT_FOUND, e.getCode());
        }
    }

    @Test
    public void testGetFriends() {
        List<User> expected = List.of(
            charlie,
            elijah
        );

        List<User> actual = userDAO.getFriends("adam");

        Assert.assertEquals(expected, actual);
    }
}