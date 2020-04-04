package com.g1t11.socialmagnetservice.model.social;

public class Friend extends User {
    public boolean isMutual;

    public Friend(String username, String fullname, boolean isFriend) {
        super(username, fullname);
        this.isMutual = isFriend;
    }
}