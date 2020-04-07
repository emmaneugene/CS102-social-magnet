package com.g1t11.socialmagnet.model.social;

/**
 * Represent a friend.
 */
public class Friend extends User {
    private boolean mutual = false;

    /**
     * Creates an empty Friend.
     */
    public Friend() {}

    /**
     * Creates a friend with the specified username, fullname, and mutual
     * condition.
     * @param username The username of the friend.
     * @param fullname The fullname of the friend.
     * @param mutual Whether the friend is a mutual friend when retrieving the
     * friends of another friend.
     */
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