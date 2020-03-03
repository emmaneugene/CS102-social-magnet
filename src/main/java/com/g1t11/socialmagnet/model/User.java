package com.g1t11.socialmagnet.model;

import java.util.List;

public class User {
    private String username;

    private String fullName;

    private List<User> friends;

    private List<Post> feed;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<Post> getFeed() {
        return feed;
    }

    public void setFeed(List<Post> feed) {
        this.feed = feed;
    }

    public String toString() {
        return String.format("%s: %s", username, fullName);
    }
}
