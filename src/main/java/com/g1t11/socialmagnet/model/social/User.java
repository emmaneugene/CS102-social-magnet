package com.g1t11.socialmagnet.model.social;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private String username = null;

    private String fullname = null;

    private List<User> friends = new ArrayList<>();

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User other = (User) o;
        return Objects.equals(username, other.username)
            && Objects.equals(fullname, other.fullname);
    }
}
