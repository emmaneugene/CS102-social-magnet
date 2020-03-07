package com.g1t11.socialmagnet.data;

import java.util.List;

import com.g1t11.socialmagnet.model.social.Comment;
import com.g1t11.socialmagnet.model.social.Post;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestPostDAO {
    private PostDAO postDAO;

    @Before
    public void initDAO() {
        Database db = new Database();
        postDAO = new PostDAO(db.connection());
    }

    @Test
    public void testGetNewsFeedPosts() {
        Post post4 = new Post(7, "elijah", "elijah", "Had a great night with adam, britney, and @charlie");
        post4.setComments(List.of(
            new Comment("britney", "How did you guys wake up so early??"),
            new Comment("adam",    "Early bird gets the worm!"),
            new Comment("elijah",  "Maybe you shouldn't stay out too late!")
        ));
        post4.setActualCommentsCount(4);
        List<Post> expected = List.of(
            new Post(10, "britney", "britney", "I'm so lonely..."),
            new Post(9,  "britney", "charlie", "We should meet up again! elijah @adsm"),
            new Post(8,  "adam",    "elijah",  "Where did you go?"),
            post4,
            new Post(5,  "charlie", "adam",    "Who are you talking to?")
        );

        List<Post> newsFeedPosts = postDAO.getNewsFeedPosts("elijah", 5);

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