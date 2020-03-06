package com.g1t11.socialmagnet.model.social;

import java.util.List;
import java.util.Objects;

public class User {
    private String username = null;

    private String fullname = null;

    private List<User> friends = null;

    private List<Post> feed = null;

    public User() {}

    public User(String username, String fullname) {
        this.username = username;
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullName(String fullname) {
        this.fullname = fullname;
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

    @Override
    public String toString() {
        String friendsNames = "";
        if (friends != null) {
            for (User friend : friends) {
                friendsNames += friend.username + ",";
            }
            friendsNames = friendsNames.substring(0, friendsNames.length() - 1);
        }
        return String.format("%s; fullname: %s; friends: %s", username, fullname, friendsNames);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User other = (User) o;
        return Objects.equals(username, other.username)
            && Objects.equals(fullname, other.fullname)
            && Objects.deepEquals(friends, other.friends)
            && Objects.deepEquals(feed, other.feed);
    }
}
