package com.g1t11.socialmagnet.model.social;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * Represent a thread.
 */
public class Thread {
    private int id;

    private String fromUsername = null;

    private String toUsername = null;

    private String content = null;

    private List<User> likers = new ArrayList<>();
    private List<User> dislikers = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>(3);
    private int actualCommentsCount = 0;

    private boolean tagged = false;

    public Thread() {}

    public Thread(int id) {
        this.id = id;
    }

    /**
     * Creates a thread with specified parameters of id, from username,
     * to username, content of the thread, count of comments and if it 
     * is tagged.
     * @param id Input id of thread.
     * @param fromUsername Username of owner of thread.
     * @param toUsername Username of user that the thread is sent to.
     * @param content Content of the thread.
     * @param commentCount Count of the comments in thread.
     * @param tagged If the thread is tagged.
     */
    public Thread(int id, String fromUsername, String toUsername,
            String content, int commentCount, boolean tagged) {
        this(id);
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.content = content;
        this.actualCommentsCount = commentCount;
        this.tagged = tagged;
    }

    /**
     * Creates a thread with specified parameters of id, from username,
     * to username, content of the thread, count of comments.
     * @param id Input id of thread.
     * @param fromUsername Username of owner of thread.
     * @param toUsername Username of user that the thread is send to.
     * @param content Content of the thread.
     * @param commentCount Count of the comments in thread.
     */
    public Thread(int id, String fromUsername, String toUsername,
            String content, int commentCount) {
        this(id, fromUsername, toUsername, content, commentCount, false);
    }
 
    /**
     * Gets id of thread.
     * @return Id of the thread.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id of thread.
     * @param id Thread id.
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    /**
     * Sets username of the owner of thread.
     * @param fromUsername Username of the owner of thread.
     */
    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    /**
     * Gets username of receiver of thread.
     * @return Username of receiver of thread.
     */
    public String getToUsername() {
        return toUsername;
    }

    /**
     * Sets username of receiver of thread.
     * @param toUsername Username of receiver of thread.
     */
    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    /**
     * Gets content of the thread.
     * @return Content of the thread.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content of the thread.
     * @param content Content of the thread.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the list of users that like the thread.
     * @return List of users that like the thread.
     */
    public List<User> getLikers() {
        return likers;
    }

    /**
     * Sets the list of users that like the thread.
     * @param likes List of users that like the thread.
     */
    public void setLikers(List<User> likes) {
        this.likers = likes;
    }

    /**
     * Gets the list of users that dislike the thread.
     * @return List of users that dislike the thread.
     */
    public List<User> getDislikers() {
        return dislikers;
    }

    /**
     * Sets the list of users that dislike the thread.
     * @param dislikes List of users that dislike the thread.
     */
    public void setDislikers(List<User> dislikes) {
        this.dislikers = dislikes;
    }

    /**
     * Gets the list of comments of the thread.
     * @return List of comments of the thread.
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Sets the list of comments of the thread.
     * @param comments List of comments of the thread.
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Gets the count of comments in thread.
     * @return Count of comments in thread.
     */
    public int getActualCommentsCount() {
        return actualCommentsCount;
    }

    /**
     * Sets the count of comments in thread.
     * @param actualCommentsCount Count of comments in thread.
     */
    public void setActualCommentsCount(int actualCommentsCount) {
        this.actualCommentsCount = actualCommentsCount;
    }

    /**
     * A method to check if there is any user tagged in thread.
     * @return The boolean value of thread is tagged.
     */
    public boolean isTagged() {
        return tagged;
    }

    /**
     * Sets if it is tagged in the thread.
     * @param tagged The boolean value of thread is tagged.
     */
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    /**
     * A method that add '@' infront to the tagged user in the content of 
     * thread.
     * @param taggedUsernames The list of tagged usernames.
     */
    public void formatContentTags(List<String> taggedUsernames) {
        for (String tag : taggedUsernames) {
            content = content.replaceFirst("@" + tag,
                    Painter.paint(tag, Color.BLUE));
        }
    }

    /**
     * Compares the specified object with this thread for equality. It returns 
     * true if and only if specified object is a thread where both thread have 
     * the same owner of thread, receiver of thread, content, likers, dislikers,
     * comments, comment counts and tagged users.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Thread)) return false;
        Thread other = (Thread) o;
        return Objects.equals(id, other.id)
                && Objects.equals(fromUsername, other.fromUsername)
                && Objects.equals(toUsername, other.toUsername)
                && Objects.equals(content, other.content)
                && Objects.deepEquals(likers, other.likers)
                && Objects.deepEquals(dislikers, other.dislikers)
                && Objects.deepEquals(comments, other.comments)
                && Objects.equals(actualCommentsCount,
                        other.actualCommentsCount)
                && Objects.equals(tagged, other.tagged);
    }
}
