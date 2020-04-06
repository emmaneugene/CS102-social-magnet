package com.g1t11.socialmagnet.model.farm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.g1t11.socialmagnet.model.social.User;

/**
 * Represent a farmer.
 */
public class Farmer extends User {
    public enum Rank {
        NOVICE("Novice"),
        APPRENTICE("Apprentice"),
        JOURNEYMAN("Journeyman"),
        GRANDMASTER("Grandmaster"),
        LEGENDARY("Legendary");

        public String value;

        private Rank(String value) {
            this.value = value;
        }
    }

    private int XP = 0;

    /**
     *  By default, new farmers start with 50 gold
     */
    private int wealth = 50;

    private int wealthRankAmongFriends = 0;

    private List<Plot> farmland = new ArrayList<>();

    /**
     * Create a farmer with specifed parameters.
     * @param username The username of farmer.
     * @param fullname The full name of farmer.
     * @param XP The experience point farmer currently have.
     * @param wealth The wealth of farmer.
     * @param wealthRank The rank of farmer based on wealth.
     */
    public Farmer(String username, String fullname, int XP,
            int wealth, int wealthRank) {
        super(username, fullname);
        this.XP = XP;
        this.wealth = wealth;
        this.wealthRankAmongFriends = wealthRank;
    }

    /**
     * Create a farmer with only username and fullname.
     * @param username The username of farmer.
     * @param fullname The full name of farmer.
     */
    public Farmer(String username, String fullname) {
        super(username, fullname);
    }

    /**
     * Create a empty farmer.
     */
    public Farmer() {}

    /**
     * Gets the rank of farmer based on current farmer's experience point.
     * @return The rank of farmer.
     */
    public Rank getRank() {
        if (XP < 1000) {
            return Rank.NOVICE;
        }
        if (XP < 2500) {
            return Rank.APPRENTICE;
        }
        if (XP < 5000) {
            return Rank.JOURNEYMAN;
        }
        if (XP < 12000) {
            return Rank.GRANDMASTER;
        }
        return Rank.LEGENDARY;
    }

    /**
     * Gets the count of maximum plot a farmer can have based on it's rank.
     * @return The number of maximum plot of farmer.
     */
    public int getMaxPlotCount() {
        switch (getRank()) {
            case NOVICE:
                return 5;
            case APPRENTICE:
                return 6;
            case JOURNEYMAN:
                return 7;
            case GRANDMASTER:
                return 8;
            default:
                return 9;
        }
    }

    /**
     * Gets the experience points of farmer.
     * @return The experience points of farmer.
     */
    public int getXP() {
        return XP;
    }

    /**
     * A method to add experience points to farmer.
     * @param XPEarned The amount of experience points to add to farmer.
     */
    public void addXP(int XPEarned) {
        XP += XPEarned;
    }

    /**
     * Gets the wealth of farmer.
     * @return The wealth of farmer.
     */
    public int getWealth() {
        return wealth;
    }

    /**
     * A method to add wealth to farmer.
     * @param wealth The amount of wealth to add to farmer.
     */
    public void addWealth(int wealth) {
        this.wealth += wealth;
    }

    /**
     * A method to subtract wealth from farmer.
     * @param wealth The amount of wealth to subtract from farmer.
     */
    public void subtractWealth(int wealth) {
        this.wealth -= wealth;
    }

    /**
     * Gets wealth rank among friends. 
     * @return The wealth rank of farmer among it's friends.
     */
    public int getWealthRankAmongFriends() {
        return wealthRankAmongFriends;
    }

    /**
     * Set wealth rank of farmer among it's friend.
     * @param wealthRank The wealth rank of farmer among it's friends
     */
    public void setWealthRankAmongFriends(int wealthRank) {
        this.wealthRankAmongFriends = wealthRank;
    }

    /**
     * Gets farmland of a farmer.
     * @return The list of plot of farmer.
     */
    public List<Plot> getFarmland() {
        return farmland;
    }

    /**
     * Compares the specified object with this farmer for equality.
     * It returns true if and only if specified object is a farmer and 
     * both farmer have the same experience point, wealth, wealth rank among
     * friends and farmland.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Farmer)) return false;
        Farmer other = (Farmer) o;
        return super.equals(other)
                && Objects.equals(XP, other.XP)
                && Objects.equals(wealth, other.wealth)
                && Objects.equals(wealthRankAmongFriends,
                        other.wealthRankAmongFriends)
                && Objects.deepEquals(farmland, other.farmland);
    }
}
