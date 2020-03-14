package com.g1t11.socialmagnet.model.social;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Thread {
    private int id;

    private String fromUsername = null;

    private String toUsername = null;

    private String content = null;

    /**
     * Stores a shallow list of User (only username and fullname) who like and dislike a post
     */
    private List<User> likers = new ArrayList<>();
    private List<User> dislikers = new ArrayList<>();

    /**
     * Only loaded when we view the thread.
     */
    private List<Comment> comments = new ArrayList<>(3);
    private int actualCommentsCount = 0;

    private boolean tagged = false;

    public Thread(int id) {
        this.id = id;
    }

    public Thread(int id, String fromUsername, String toUsername, String content, int commentCount, boolean tagged) {
        this(id);
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.content = content;
        this.actualCommentsCount = commentCount;
        this.tagged = tagged;
    }

    public Thread(int id, String fromUsername, String toUsername, String content, int commentCount) {
        this(id, fromUsername, toUsername, content, commentCount, false);
    }

    public int getId() {
        return id;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public List<User> getLikers() {
        return likers;
    }

    public void setLikers(List<User> likes) {
        this.likers = likes;
    }

    public List<User> getDislikers() {
        return dislikers;
    }

    public void setDislikers(List<User> dislikes) {
        this.dislikers = dislikes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getActualCommentsCount() {
        return actualCommentsCount;
    }

    public void setActualCommentsCount(int actualCommentsCount) {
        this.actualCommentsCount = actualCommentsCount;
    }

    public boolean isTagged() {
        return tagged;
    }


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
            && Objects.equals(actualCommentsCount, other.actualCommentsCount)
            && Objects.equals(tagged, other.tagged);
    }
}
