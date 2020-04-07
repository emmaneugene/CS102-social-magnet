package com.g1t11.socialmagnet.model.social;

import java.util.Objects;

/**
 * Represent a comment.
 */
public class Comment {
    private String username;

    private String content;

    /**
     * Creates an empty comment.
     */
    public Comment() {}

    /**
     * Creates a comment with specified username with content.
     * @param username The username that commented.
     * @param content The content of the comment.
     */
    public Comment(String username, String content) {
        this.username = username;
        this.content = content;
    }

    /**
     * Gets the username of the comment.
     * @return The username of the comment.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username to the comment.
     * @param username the username to set to the comment.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the content of the comment.
     * @return The content of the comment.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the comment.
     * @param content The content of the comment to be set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Compares the specified object with this comment for equality.
     * It returns true if and only if specified object is a comment and
     * both comment have the same username and same content of the comment.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Comment)) return false;
        Comment other = (Comment) o;
        return Objects.equals(username, other.username)
                && Objects.equals(content, other.content);
    }
}

