package com.g1t11.socialmagnet.model.social;

import java.util.Objects;

/**
 * Represent a user.
 */
public class User {
    private String username = null;

    private String fullname = null;

    /**
     * Creates a empty user.
     */
    public User() {}

    /**
     * Creates a user with specified username and fullname.
     * @param username Input username.
     * @param fullname Input fullname.
     */
    public User(String username, String fullname) {
        this.username = username;
        this.fullname = fullname;
    }

    /**
     * Gets username of user.
     * @return Username of user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username of user.
     * @param username Username of user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets fullname of user.
     * @return Fullname of user.
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Sets fullname of user.
     * @param fullname Fullname of user.
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Compares the specified object with this user for equality. It returns 
     * true if and only if specified object is a user where both users have 
     * the same username and fullname.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User other = (User) o;
        return Objects.equals(username, other.username)
                && Objects.equals(fullname, other.fullname);
    }
}
