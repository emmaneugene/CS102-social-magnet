package com.g1t11.socialmagnet.data;

import java.util.List;

import com.g1t11.socialmagnet.model.social.Comment;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestThreadDAO {
    private ThreadDAO threadDAO;

    @Before
    public void initDAO() {
        Database db = new Database();
        threadDAO = new ThreadDAO(db.connection());
    }

    @Test
    public void testGetThread() {
        // Testing from the perspective of adam
        Thread expected = new Thread(7, "elijah", "elijah", "Had a great night with adam, britney, and @charlie", true);
        expected.setActualCommentsCount(4);
        expected.setComments(List.of(
            new Comment("britney", "How did you guys wake up so early??"),
            new Comment("adam", "Early bird gets the worm!"),
            new Comment("elijah", "Maybe you shouldn't stay out too late!")
        ));
        expected.setLikers(List.of(
            new User("britney", "Britney Spears")
        ));

        Thread thread = threadDAO.getThread(7, new User("adam", "Adam Levine"));

        Assert.assertEquals(expected, thread);
    }

    @Test
    public void testGetNewsFeedThreads() {
        List<Thread> expected = List.of(
            new Thread(10, "britney", "britney", "I'm so lonely..."),
            new Thread(9,  "britney", "charlie", "We should meet up again! elijah @adsm", true),
            new Thread(8,  "adam",    "elijah",  "Where did you go?"),
            new Thread(7,  "elijah",  "elijah",  "Had a great night with adam, britney, and @charlie"),
            new Thread(5,  "charlie", "adam",    "Who are you talking to?")
        );

        List<Thread> threads = threadDAO.getNewsFeedThreads(new User("elijah", "Elijah Wood"), 5);

        // Assert only the shallow details of threads
        for (int i = 0; i < expected.size(); i++) {
            Thread exp = expected.get(i);
            Thread act = threads.get(i);
            Assert.assertEquals(exp.getFromUsername(), act.getFromUsername());
            Assert.assertEquals(exp.getToUsername(), act.getToUsername());
            Assert.assertEquals(exp.getContent(), act.getContent());
            Assert.assertEquals(exp.isTagged(), act.isTagged());
        }
    }

    @Test
    public void testGetCommentsCount() {
        Assert.assertEquals(4, threadDAO.getCommentsCount(new Thread(7)));
    }

    @Test
    public void testGetComments() {
        List<Comment> expected = List.of(
            new Comment("britney", "How did you guys wake up so early??"),
            new Comment("adam",    "Early bird gets the worm!"),
            new Comment("elijah",  "Maybe you shouldn't stay out too late!")
        );

        List<Comment> comments = threadDAO.getCommentsLatestLast(new Thread(7), 3);

        Assert.assertEquals(expected, comments);
    }

    @Test
    public void testGetLikers() {
        List<User> expected = List.of(
            new User("adam", "Adam Levine"),
            new User("britney", "Britney Spears")
        );

        List<User> likers = threadDAO.getLikers(new Thread(3));

        Assert.assertEquals(expected, likers);
    }

    @Test
    public void testGetDislikers() {
        List<User> expected = List.of(
            new User("adam", "Adam Levine"),
            new User("britney", "Britney Spears")
        );

        List<User> dislikers = threadDAO.getDislikers(new Thread(4));

        Assert.assertEquals(expected, dislikers);
    }

    /**
     * When performing destructive tests on the database, be sure to not affect
     * records that are used for regular tests.
     */
    @Test
    public void testAddAndRemoveTag() {
        Thread thread = new Thread(8);
        User user = new User("elijah", "Elijah Wood");
        Thread retrieved;

        threadDAO.addTag(thread, user);
        retrieved = threadDAO.getThread(8, user);
        Assert.assertTrue(retrieved.isTagged());

        threadDAO.removeTag(thread, user);
        retrieved = threadDAO.getThread(8, user);
        Assert.assertFalse(retrieved.isTagged());
    }

    @Test
    public void testDeletePost() {
        Thread thread = new Thread(8);
        User user = new User("adam", "Adam Levine");
        threadDAO.deleteThread(thread, user);
    }

    @Test
    public void testLikeThread() {
        Thread thread = new Thread(8);
        User user = new User("adam", "Adam Levine");

        threadDAO.likeThread(thread, user);
    }

    @Test
    public void testDislikeThread() {
        Thread thread = new Thread(8);
        User user = new User("adam", "Adam Levine");

        threadDAO.dislikeThread(thread, user);
    }
}