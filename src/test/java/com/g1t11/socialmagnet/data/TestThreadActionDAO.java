package com.g1t11.socialmagnet.data;

import java.util.List;

import com.g1t11.socialmagnet.model.social.Comment;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.ThreadNotFoundException;
import com.g1t11.socialmagnet.model.social.User;

import org.junit.Assert;
import org.junit.Test;

public class TestThreadActionDAO extends TestDAO {
    private ThreadLoadDAO threadLoadDAO;
    private ThreadActionDAO threadActionDAO;

    public static final User adam    = new User("adam", "Adam Levine");
    public static final User britney = new User("britney", "Britney Spears");
    public static final User charlie = new User("charlie", "Charlie Puth");
    public static final User danny   = new User("danny", "Danny DeVito");
    public static final User elijah  = new User("elijah", "Elijah Wood");
    public static final User frank   = new User("frank", "Frank Sinatra");
    public static final User gary    = new User("gary", "Gary Oldman");
    public static final User howard  = new User("howard", "Howard Duck");
    public static final User icarus  = new User("icarus", "Icarus");

    public TestThreadActionDAO() {
        threadLoadDAO = new ThreadLoadDAO(db);
        threadActionDAO = new ThreadActionDAO(db);
    }

    @Test
    public void testAddThreadAddTags() {
        int newId = threadActionDAO.addThread("adam", "adam", "Hello @charlie and @britney!", List.of("charlie", "britney"));
        Thread expected = new Thread(newId, "adam", "adam", "Hello @charlie and @britney!", 0);
        // charlie is a friend, britney is not.
        expected.formatContentTags(List.of("charlie"));

        Thread actual = threadLoadDAO.getThread(newId, "adam");
        Assert.assertEquals(expected, actual);

        // Check if tags were loaded properly.
        List<String> expectedTags = List.of("charlie");
        List<String> actualTags = threadLoadDAO.getTaggedUsernames(newId);
        Assert.assertEquals(expectedTags, actualTags);
    }

    @Test
    public void testAddAndRemoveTags() {
        Thread actual = threadLoadDAO.getThread(10, "elijah");
        Assert.assertFalse(actual.isTagged());

        // Add and remove tags from thread 10 on database
        threadActionDAO.addTags(10, List.of("elijah"));
        actual = threadLoadDAO.getThread(10, "elijah");
        Assert.assertTrue(actual.isTagged());

        threadActionDAO.removeTag(10, "elijah");
        actual = threadLoadDAO.getThread(10, "elijah");
        Assert.assertFalse(actual.isTagged());
    }

    @Test(expected = ThreadNotFoundException.class)
    public void testDeleteThreadNoAttributes() {
        threadActionDAO.deleteThread(5, "charlie");
        threadLoadDAO.getThread(5, "charlie");
    }

    @Test(expected = ThreadNotFoundException.class)
    public void testDeleteThreadWithLikesDislikesCommentsTags() {
        threadActionDAO.deleteThread(6, "danny");
        threadLoadDAO.getThread(6, "danny");
    }

    @Test
    public void testDeleteUnauthorized() {
        Thread before = threadLoadDAO.getThread(9, "britney");
        // elijah is tagged, but is not a sender or recipient of thread 9
        threadActionDAO.deleteThread(9, "elijah");
        Thread after = threadLoadDAO.getThread(9, "britney");
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
        expected.getComments().remove(0);
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
