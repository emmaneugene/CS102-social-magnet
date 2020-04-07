package com.g1t11.socialmagnet.model.social;

public class Friend extends User {
    private boolean mutual = false;

    public Friend() {}

    public Friend(String username, String fullname, boolean mutual) {
        super(username, fullname);
        this.mutual = mutual;
    }

    public boolean isMutual() {
        return mutual;
    }

    public void setMutual(boolean mutual) {
        this.mutual = mutual;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Friend)) return false;
        Friend other = (Friend) o;
        return (mutual == other.mutual) && super.equals(o);
    }
}