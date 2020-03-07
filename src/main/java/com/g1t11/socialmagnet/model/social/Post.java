package com.g1t11.socialmagnet.model.social;

import java.util.List;
import java.util.Objects;

public class Post {
    private int id;

    private String fromUsername = null;

    private String toUsername = null;

    private String content = null;

    /**
     * Stores a shallow list of User (only username and fullname) who like and dislike a post
     */
    private List<User> likes = null;
    private List<User> dislikes = null;

    /**
     * Only loaded when we view the thread.
     */
    private List<Comment> comments = null;

    public Post(int id) {
        this.id = id;
    }

    public Post(int id, String fromUsername, String toUsername, String content) {
        this(id);
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.content = content;
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

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public List<User> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<User> dislikes) {
        this.dislikes = dislikes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Post)) return false;
        Post other = (Post) o;
        return Objects.equals(id, other.id)
            && Objects.equals(fromUsername, other.fromUsername)
            && Objects.equals(toUsername, other.toUsername)
            && Objects.equals(content, other.content)
            && Objects.deepEquals(likes, other.likes)
            && Objects.deepEquals(dislikes, other.dislikes)
            && Objects.deepEquals(comments, other.comments);
    }
}
