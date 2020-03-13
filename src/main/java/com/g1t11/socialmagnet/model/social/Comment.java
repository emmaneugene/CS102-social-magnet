package com.g1t11.socialmagnet.model.social;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Comment)) return false;
        Comment other = (Comment) o;
        return Objects.equals(username, other.username)
            && Objects.equals(content, other.content);
    }
}
    