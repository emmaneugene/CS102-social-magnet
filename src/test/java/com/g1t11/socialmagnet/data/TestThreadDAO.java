package com.g1t11.socialmagnet.data;

import java.util.List;

import com.g1t11.socialmagnet.model.social.Comment;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.ThreadNotFoundException;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;

import org.junit.Assert;
import org.junit.Test;

public class TestThreadDAO extends TestDAO {
    private ThreadLoadDAO threadDAO;

    public static final User adam    = new User("adam", "Adam Levine");
    public static final User britney = new User("britney", "Britney Spears");
    public static final User charlie = new User("charlie", "Charlie Puth");
    public static final User danny   = new User("danny", "Danny DeVito");
    public static final User elijah  = new User("elijah", "Elijah Wood");
    public static final User frank   = new User("frank", "Frank Sinatra");
    public static final User gary    = new User("gary", "Gary Oldman");
    public static final User howard  = new User("howard", "Howard Duck");
    public static final User icarus  = new User("icarus", "Icarus");

    public TestThreadDAO() {
        threadDAO = new ThreadLoadDAO(db);
    }

    @Test
    public void testGetThread() {
        // Testing from the perspective of adam
        Thread expected = new Thread(
            7, "elijah", "elijah",
            Painter.paintf("Had a great night with [{adam}], [{britney}], and @charlie", Painter.Color.BLUE), 
            4, true
        );
        expected.setActualCommentsCount(4);
        expected.setComments(List.of(
            new Comment("britney", "How did you guys wake up so early??"),
            new Comment("adam",    "Early bird gets the worm!"),
            new Comment("elijah",  "Maybe you shouldn't stay out too late!")
        ));
        expected.setLikers(List.of(britney));

        Thread actual = threadDAO.getThread(7, "adam");

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = ThreadNotFoundException.class)
    public void testGetNonExistentThread() {
        threadDAO.getThread(100, "adam");
    }

    @Test
    public void testSetCommentsOnlyThree() {
        List<Comment> expected = List.of(
            new Comment("charlie", "Good job! You started programming."),
            new Comment("elijah",  "Bye!"),
            new Comment("charlie", "Goodbye!")
        );
        Thread actual = new Thread(1);
        threadDAO.setCommentsLatestLast(actual, 3);

        Assert.assertEquals(expected, actual.getComments());
    }

    @Test
    public void testSetCommentsFewerThanThree() {
        List<Comment> expected = List.of(
            new Comment("charlie", "Same!!! Too many things to do!")
        );
        Thread actual = new Thread(2);
        threadDAO.setCommentsLatestLast(actual, 3);

        Assert.assertEquals(expected, actual.getComments());
    }

    @Test
    public void testSetCommentsMoreThanThree() {
        List<Comment> expected = List.of(
            new Comment("britney", "How did you guys wake up so early??"),
            new Comment("adam",    "Early bird gets the worm!"),
            new Comment("elijah",  "Maybe you shouldn't stay out too late!")
        );
        Thread actual = new Thread(7);
        threadDAO.setCommentsLatestLast(actual, 3);

        Assert.assertEquals(expected, actual.getComments());
    }

    @Test
    public void testSetCommentsZero() {
        Thread actual = new Thread(3);
        threadDAO.setCommentsLatestLast(actual, 3);

        Assert.assertEquals(0, actual.getComments().size());
    }

    @Test
    public void testSetLikers() {
        List<User> expected = List.of(
            new User("adam", "Adam Levine"),
            new User("britney", "Britney Spears")
        );

        Thread actual = new Thread(3);
        threadDAO.setLikers(actual);

        Assert.assertEquals(expected, actual.getLikers());
    }

    @Test
    public void testSetLikersNone() {
        Thread actual = new Thread(4);
        threadDAO.setLikers(actual);

        Assert.assertEquals(0, actual.getLikers().size());
    }

    @Test
    public void testSetDislikers() {
        List<User> expected = List.of(
            new User("adam", "Adam Levine"),
            new User("britney", "Britney Spears")
        );

        Thread actual = new Thread(4);
        threadDAO.setDislikers(actual);

        Assert.assertEquals(expected, actual.getDislikers());
    }

    @Test
    public void testSetDislikersNone() {
        Thread actual = new Thread(2);
        threadDAO.setDislikers(actual);

        Assert.assertEquals(0, actual.getDislikers().size());
    }

    @Test
    public void testGetTaggedUsernames() {
        List<String> expected = List.of("adam", "britney");

        List<String> actual = threadDAO.getTaggedUsernames(7);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetTaggedUsernamesNone() {
        List<String> actual = threadDAO.getTaggedUsernames(8);
        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void testGetNewsFeedThreads() {
        List<Thread> expected = List.of(
            threadDAO.getThread(10, "elijah"),
            threadDAO.getThread(9,  "elijah"),
            threadDAO.getThread(8,  "elijah"),
            threadDAO.getThread(7,  "elijah"),
            threadDAO.getThread(5,  "elijah")
        );

        List<Thread> actual = threadDAO.getNewsFeedThreads("elijah", 5);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetNewsFeedThreadsNone() {
        List<Thread> actual = threadDAO.getNewsFeedThreads("gary", 5);

        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void testGetWallThreads() {
        List<Thread> expected = List.of(
            threadDAO.getThread(7, "adam"),
            threadDAO.getThread(5, "adam"),
            threadDAO.getThread(2, "adam"),
            threadDAO.getThread(1, "adam")
        );

        List<Thread> actual = threadDAO.getWallThreads("adam", 5);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetWallThreadsNone() {
        List<Thread> actual = threadDAO.getWallThreads("gary", 5);

        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void testAddAndRemoveTags() {
        Thread retrieved;

        threadDAO.addTags(8, List.of("elijah"));
        retrieved = threadDAO.getThread(8, "elijah");
        Assert.assertTrue(retrieved.isTagged());

        threadDAO.removeTag(8, "elijah");
        retrieved = threadDAO.getThread(8, "elijah");
        Assert.assertFalse(retrieved.isTagged());
    }

    @Test
    public void testAddTags() {
        Thread retrieved;

        threadDAO.addTags(6, List.of("adam", "britney", "charlie", "danny", "elijah", "frank"));
        retrieved = threadDAO.getThread(6, "charlie");
        Assert.assertTrue(retrieved.isTagged());

        retrieved = threadDAO.getThread(6, "frank");
        Assert.assertTrue(retrieved.isTagged());
    }
}
