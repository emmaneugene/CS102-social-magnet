package com.g1t11.socialmagnet.model.social;

public class CommonFriend extends User {
    public CommonFriend(String username, String fullname) {
        super(username, fullname);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CommonFriend)) return false;
        User other = (User) o;
        return super.equals(other);
    }
}