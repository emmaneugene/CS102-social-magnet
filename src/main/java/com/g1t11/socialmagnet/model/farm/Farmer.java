package com.g1t11.socialmagnet.model.farm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.g1t11.socialmagnet.model.social.User;

/**
 * Farmer extends User
 * - rank : String
 * - XP : int
 * - wealth : int
 * - farmland : ArrayList<Plot>
 * - inventory : ArrayList<InventoryItem>
 * + getRank() : String
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

    public Farmer(String username, String fullname, int XP, int wealth, int wealthRank) {
        super(username, fullname);
        this.XP = XP;
        this.wealth = wealth;
        this.wealthRankAmongFriends = wealthRank;
    }

    public Farmer(String username, String fullname) {
        super(username, fullname);
    }

    public Farmer() {}

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

    public int getXP() {
        return XP;
    }

    /**
     * Convenience method to add XP
     */
    public void addXP(int XPEarned) {
        XP += XPEarned;
    }

    public int getWealth() {
        return wealth;
    }

    public void addWealth(int wealth) {
        this.wealth += wealth;
    }

    public void subtractWealth(int wealth) {
        this.wealth -= wealth;
    }

    public int getWealthRankAmongFriends() {
        return wealthRankAmongFriends;
    }

    public void setWealthRankAmongFriends(int wealthRank) {
        this.wealthRankAmongFriends = wealthRank;
    }

    public List<Plot> getFarmland() {
        return farmland;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Farmer)) return false;
        Farmer other = (Farmer) o;
        return super.equals(other)
        && Objects.equals(XP, other.XP)
        && Objects.equals(wealth, other.wealth)
        && Objects.equals(wealthRankAmongFriends, other.wealthRankAmongFriends)
        && Objects.deepEquals(farmland, other.farmland);
    }
}
