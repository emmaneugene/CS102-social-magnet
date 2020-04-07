package com.g1t11.socialmagnet.data.rest;

import java.util.List;

import com.g1t11.socialmagnet.data.ServerException;
import com.g1t11.socialmagnet.data.ServerException.SQLErrorCode;
import com.g1t11.socialmagnet.model.social.Friend;
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
        } catch (ServerException e) {
            Assert.assertTrue(
                    SQLErrorCode.USER_NOT_FOUND.value == e.getCode());
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

    @Test
    public void testGetNoFriends() {
        List<User> actual = userDAO.getFriends("gary");

        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void testGetFriendsOfNonExistentUser() {
        List<User> actual = userDAO.getFriends("ADAM");

        Assert.assertEquals(0, actual.size());
    }


    @Test
    public void testGetFriendsOfFriendNoCommon() {
        List<User> expected = List.of(
            britney,
            danny
        );

        List<Friend> actual
                = userDAO.getFriendsOfFriendWithCommon("adam", "elijah");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testMakeAcceptUnfriendMakeReject() {
        userDAO.makeRequest("howard", "icarus");
        testAcceptRequest();
        testUnfriend();
        userDAO.makeRequest("howard", "icarus");
        testRejectRequest();
    }

    @Test
    public void testRequestSelf() {
        try {
            userDAO.makeRequest("howard", "howard");
            Assert.assertTrue(false);
        } catch (ServerException e) {
            Assert.assertTrue(
                    SQLErrorCode.REQUEST_SELF.value == e.getCode());
        }
    }

    private void testAcceptRequest() {
        userDAO.acceptRequest("howard", "icarus");

        List<User> expected = List.of(icarus);
        List<User> actual = userDAO.getFriends("howard");

        Assert.assertEquals(expected, actual);
    }

    private void testRejectRequest() {
        userDAO.rejectRequest("howard", "icarus");

        List<User> actual = userDAO.getFriends("howard");

        Assert.assertEquals(0, actual.size());
    }

    private void testUnfriend() {
        userDAO.unfriend("icarus", "howard");

        List<User> actualHoward = userDAO.getFriends("howard");
        List<User> actualIcarus = userDAO.getFriends("icarus");

        Assert.assertEquals(0, actualHoward.size());
        Assert.assertEquals(0, actualIcarus.size());
    }

    @Test
    public void testGetRequestUsernames() {
        List<String> expected = List.of("adam", "charlie");
        List<String> actual = userDAO.getRequestUsernames("gary");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetNoRequests() {
        List<String> actual = userDAO.getRequestUsernames("britney");
        Assert.assertEquals(0, actual.size());
    }
}