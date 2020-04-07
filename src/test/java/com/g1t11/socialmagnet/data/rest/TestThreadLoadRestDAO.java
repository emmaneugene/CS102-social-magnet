package com.g1t11.socialmagnet.data.rest;

import java.util.List;

import com.g1t11.socialmagnet.data.ServerException;
import com.g1t11.socialmagnet.data.ServerException.SQLErrorCode;
import com.g1t11.socialmagnet.model.social.Comment;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

import org.junit.Assert;
import org.junit.Test;

public class TestThreadLoadRestDAO {
    private ThreadLoadRestDAO threadLoadDAO;

    public static final User adam    = new User("adam", "Adam Levine");
    public static final User britney = new User("britney", "Britney Spears");
    public static final User charlie = new User("charlie", "Charlie Puth");
    public static final User danny   = new User("danny", "Danny DeVito");
    public static final User elijah  = new User("elijah", "Elijah Wood");
    public static final User frank   = new User("frank", "Frank Sinatra");
    public static final User gary    = new User("gary", "Gary Oldman");
    public static final User howard  = new User("howard", "Howard Duck");
    public static final User icarus  = new User("icarus", "Icarus");

    public TestThreadLoadRestDAO() {
        threadLoadDAO = new ThreadLoadRestDAO();
    }

    @Test
    public void testGetThread() {
        // Testing from the perspective of adam
        Thread expected = new Thread(
                7, "elijah", "elijah",
                Painter.paintf(
                        "Had a great night with [{adam}], [{britney}],"
                                + " and @charlie",
                        Color.BLUE),
                4, true
        );
        expected.setActualCommentsCount(4);
        expected.setComments(List.of(
            new Comment("adam", "You were a blast!"),
            new Comment("britney", "How did you guys wake up so early??"),
            new Comment("adam",    "Early bird gets the worm!"),
            new Comment("elijah",  "Maybe you shouldn't stay out too late!")));
        expected.setLikers(List.of(britney));

        Thread actual = threadLoadDAO.getThread(7, "adam");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetNonExistentThread() {
        try {
            threadLoadDAO.getThread(100, "adam");
            Assert.assertTrue(false);
        } catch (ServerException e) {
            Assert.assertTrue(
                    SQLErrorCode.THREAD_NOT_FOUND.value == e.getCode());
        }
    }

    @Test
    public void testGetNewsFeedThreads() {
        List<Thread> expected = List.of(
            threadLoadDAO.getThread(19, "lary"),
            threadLoadDAO.getThread(18, "lary"),
            threadLoadDAO.getThread(17, "lary"));

        List<Thread> actual = threadLoadDAO.getNewsFeedThreads("lary", 5);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetNewsFeedThreadsNone() {
        List<Thread> actual = threadLoadDAO.getNewsFeedThreads("gary", 5);

        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void testGetWallThreads() {
        List<Thread> expected = List.of(
            threadLoadDAO.getThread(15, "lary"),
            threadLoadDAO.getThread(19, "lary"),
            threadLoadDAO.getThread(18, "lary"),
            threadLoadDAO.getThread(17, "lary"),
            threadLoadDAO.getThread(16, "lary"));

        List<Thread> actual = threadLoadDAO.getWallThreads("lary", 5);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetWallThreadsNone() {
        List<Thread> actual = threadLoadDAO.getWallThreads("gary", 5);

        Assert.assertEquals(0, actual.size());
    }
}