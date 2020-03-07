package com.g1t11.socialmagnet.model.social;

/**
 * Comment
 */
public class Comment {
    private String username;
    private String content;

    public Comment() {}

    public Comment(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
    