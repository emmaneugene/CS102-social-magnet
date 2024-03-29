package com.g1t11.socialmagnet.data.rest;

import java.util.List;

import com.g1t11.socialmagnet.data.ServerException;
import com.g1t11.socialmagnet.data.ServerException.ErrorCode;
import com.g1t11.socialmagnet.model.social.Comment;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;

import org.junit.Assert;
import org.junit.Test;

public class TestThreadActionRestDAO {
    private ThreadLoadRestDAO threadLoadDAO;
    private ThreadActionRestDAO threadActionDAO;

    public static final User adam    = new User("adam", "Adam Levine");
    public static final User britney = new User("britney", "Britney Spears");
    public static final User charlie = new User("charlie", "Charlie Puth");
    public static final User danny   = new User("danny", "Danny DeVito");
    public static final User elijah  = new User("elijah", "Elijah Wood");
    public static final User frank   = new User("frank", "Frank Sinatra");
    public static final User gary    = new User("gary", "Gary Oldman");
    public static final User howard  = new User("howard", "Howard Duck");
    public static final User icarus  = new User("icarus", "Icarus");

    public TestThreadActionRestDAO() {
        threadLoadDAO = new ThreadLoadRestDAO();
        threadActionDAO = new ThreadActionRestDAO();
    }

    @Test
    public void testAddThreadAddTagsDeleteThread() {
        int newId = threadActionDAO.addThread("adam", "adam",
                "Hello @charlie and @britney!", List.of("charlie", "britney"));
        Thread expected = new Thread(newId, "adam", "adam",
                "Hello @charlie and @britney!", 0);
        // charlie is a friend, britney is not.
        expected.formatContentTags(List.of("charlie"));

        Thread actual = threadLoadDAO.getThread(newId, "adam");
        Assert.assertEquals(expected, actual);

        // // Check if tags were loaded properly.
        // List<String> expectedTags = List.of("charlie");
        // List<String> actualTags = threadLoadDAO.getTaggedUsernames(newId);
        // Assert.assertEquals(expectedTags, actualTags);

        // Delete the new thread.
        threadActionDAO.deleteThread(newId, "adam");

        // Check if tags were deleted.
        // actualTags = threadLoadDAO.getTaggedUsernames(newId);
        // Assert.assertEquals(0, actualTags.size());

        // Check that the thread does not exist
        try {
            threadLoadDAO.getThread(newId, "adam");
            Assert.assertTrue(false);
        } catch (ServerException e) {
            Assert.assertTrue(
                    ErrorCode.THREAD_NOT_FOUND.value == e.getCode());
        }
    }

    @Test
    public void testDeleteThreadNoAttributes() {
        threadActionDAO.deleteThread(11, "charlie");
        try {
            threadLoadDAO.getThread(11, "charlie");
            Assert.assertTrue(false);
        } catch (ServerException e) {
            Assert.assertTrue(
                    ErrorCode.THREAD_NOT_FOUND.value == e.getCode());
        }
    }

    @Test
    public void testDeleteThreadWithLikesDislikesCommentsTags() {
        threadActionDAO.deleteThread(12, "danny");
        try {
            threadLoadDAO.getThread(12, "danny");
            Assert.assertTrue(false);
        } catch (ServerException e) {
            Assert.assertTrue(
                    ErrorCode.THREAD_NOT_FOUND.value == e.getCode());
        }
    }

    @Test
    public void testDeleteUnauthorized() {
        Thread before = threadLoadDAO.getThread(13, "britney");
        // elijah is tagged, but is not a sender or recipient of thread 9
        threadActionDAO.deleteThread(13, "elijah");
        Thread after = threadLoadDAO.getThread(13, "britney");
        Assert.assertEquals(before, after);
    }

    @Test
    public void testReplyToThread() {
        Thread expected = threadLoadDAO.getThread(4, "charlie");
        expected.getComments().add(new Comment("charlie", "Working now."));
        threadActionDAO.replyToThread(4, "charlie", "Working now.");
        Thread actual = threadLoadDAO.getThread(4, "charlie");
        Assert.assertEquals(expected.getComments(), actual.getComments());
    }

    @Test
    public void testReplyToThreadMoreThanThree() {
        Thread expected = threadLoadDAO.getThread(8, "adam");
        expected.getComments().add(new Comment("adam", "Fourth!"));
        threadActionDAO.replyToThread(8, "adam", "Fourth!");
        Thread actual = threadLoadDAO.getThread(8, "adam");
        Assert.assertEquals(expected.getComments(), actual.getComments());
    }

    @Test
    public void testReplyToThreadNoComments() {
        Thread expected = threadLoadDAO.getThread(10, "britney");
        expected.getComments().add(new Comment("britney", "First comment!"));
        threadActionDAO.replyToThread(10, "britney", "First comment!");
        Thread actual = threadLoadDAO.getThread(10, "britney");
        Assert.assertEquals(expected.getComments(), actual.getComments());
    }

    @Test
    public void testToggleLikeThread() {
        Thread expected = threadLoadDAO.getThread(1, "elijah");

        expected.getLikers().add(elijah);
        threadActionDAO.toggleLikeThread(1, "elijah");
        Thread actual = threadLoadDAO.getThread(1, "elijah");
        Assert.assertEquals(expected.getLikers(), actual.getLikers());

        expected.getLikers().remove(elijah);
        threadActionDAO.toggleLikeThread(1, "elijah");
        actual = threadLoadDAO.getThread(1, "elijah");
        Assert.assertEquals(expected.getLikers(), actual.getLikers());
    }

    @Test
    public void testToggleDislikeThread() {
        Thread expected = threadLoadDAO.getThread(3, "elijah");

        expected.getDislikers().add(elijah);
        threadActionDAO.toggleDislikeThread(3, "elijah");
        Thread actual = threadLoadDAO.getThread(3, "elijah");
        Assert.assertEquals(expected.getDislikers(), actual.getDislikers());

        expected.getDislikers().remove(elijah);
        threadActionDAO.toggleDislikeThread(3, "elijah");
        actual = threadLoadDAO.getThread(3, "elijah");
        Assert.assertEquals(expected.getDislikers(), actual.getDislikers());
    }
}