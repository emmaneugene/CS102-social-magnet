package com.g1t11.socialmagnet.data;

import java.util.List;

import com.g1t11.socialmagnet.TestApp;
import com.g1t11.socialmagnet.model.social.Post;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestPostDAO extends TestApp {
    private PostDAO postDAO;

    @Before
    public void initDAO() {
        postDAO = new PostDAO(app.db.connection());
    }

    @Test
    public void testGetNewsFeedPosts() {
        List<Post> expected = List.of(
            new Post(5, "charlie", "adam",   "Who are you talking to?"),
            new Post(7, "elijah",  "elijah", "Had a great night with adam, britney, and @charkie"),
            new Post(3, "britney", "elijah", "Don't know what you're talking about"),
            new Post(2, "adam",    "adam",   "I'm going crazy!!"),
            new Post(1, "adam",    "adam",   "Hello, world!")
        );

        List<Post> newsFeedPosts = postDAO.getNewsFeedPosts("elijah", 5);

        Assert.assertEquals(expected, newsFeedPosts);
    }
}