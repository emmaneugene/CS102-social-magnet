package com.g1t11.socialmagnet.data;

import java.util.List;

import com.g1t11.socialmagnet.model.social.Comment;
import com.g1t11.socialmagnet.model.social.Thread;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestThreadDAO {
    private ThreadDAO postDAO;

    @Before
    public void initDAO() {
        Database db = new Database();
        postDAO = new ThreadDAO(db.connection());
    }

    @Test
    public void testGetNewsFeedPosts() {
        Thread post4 = new Thread(7, "elijah", "elijah", "Had a great night with adam, britney, and @charlie");
        post4.setComments(List.of(
            new Comment("britney", "How did you guys wake up so early??"),
            new Comment("adam",    "Early bird gets the worm!"),
            new Comment("elijah",  "Maybe you shouldn't stay out too late!")
        ));
        post4.setActualCommentsCount(4);
        List<Thread> expected = List.of(
            new Thread(10, "britney", "britney", "I'm so lonely..."),
            new Thread(9,  "britney", "charlie", "We should meet up again! elijah @adsm"),
            new Thread(8,  "adam",    "elijah",  "Where did you go?"),
            post4,
            new Thread(5,  "charlie", "adam",    "Who are you talking to?")
        );

        List<Thread> newsFeedPosts = postDAO.getNewsFeedThreads("elijah", 5);

        Assert.assertEquals(expected, newsFeedPosts);
    }

    @Test
    public void testGetCommentsCount() {
        Assert.assertEquals(4, postDAO.getCommentsCount(7));
    }

    @Test
    public void testGetComments() {
        List<Comment> expected = List.of(
            new Comment("britney", "How did you guys wake up so early??"),
            new Comment("adam",    "Early bird gets the worm!"),
            new Comment("elijah",  "Maybe you shouldn't stay out too late!")
        );

        List<Comment> comments = postDAO.getCommentsLatestLast(7, 3);

        Assert.assertEquals(expected, comments);
    }
}