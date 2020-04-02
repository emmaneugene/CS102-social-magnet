package com.g1t11.socialmagnet.model.social;

/**
 * Represent a user that is a common friend of 2 other users.
 */
public class CommonFriend extends User {

    /**
     * Create a user that is a common friend of 2 other users with specified
     * username and fullname.
     * @param username The username of common friend.
     * @param fullname The fullname of common friend.
     */
    public CommonFriend(String username, String fullname) {
        super(username, fullname);
    }

    /**
     * Create a user that is a common friend of 2 other users with specified
     * user.
     * @param user The user class of common friend.
     */
    public CommonFriend(User user) {
        this(user.getUsername(), user.getFullname());
    }

    /**
     * Compares the specified object with this user that is a common friend
     * for equality. It returns true if and only if specified object 
     * is a user that is a common friend and both are the same user..
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CommonFriend)) return false;
        User other = (User) o;
        return super.equals(other);
    }
}
