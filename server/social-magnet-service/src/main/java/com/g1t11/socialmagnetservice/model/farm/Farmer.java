package com.g1t11.socialmagnetservice.model.farm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.g1t11.socialmagnetservice.model.social.User;

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

    private int xp = 0;

    /**
     *  By default, new farmers start with 50 gold
     */
    private int wealth = 50;

    private int wealthRankAmongFriends = 0;

    private List<Plot> farmland = new ArrayList<>();

    public Farmer() {}

    public Farmer(String username, String fullname, int XP,
            int wealth, int wealthRank) {
        super(username, fullname);
        this.xp = XP;
        this.wealth = wealth;
        this.wealthRankAmongFriends = wealthRank;
    }

    public Farmer(String username, String fullname) {
        super(username, fullname);
    }

    // public Rank getRank() {
    //     if (xp < 1000) {
    //         return Rank.NOVICE;
    //     }
    //     if (xp < 2500) {
    //         return Rank.APPRENTICE;
    //     }
    //     if (xp < 5000) {
    //         return Rank.JOURNEYMAN;
    //     }
    //     if (xp < 12000) {
    //         return Rank.GRANDMASTER;
    //     }
    //     return Rank.LEGENDARY;
    // }

    // public int getMaxPlotCount() {
    //     switch (getRank()) {
    //         case NOVICE:
    //             return 5;
    //         case APPRENTICE:
    //             return 6;
    //         case JOURNEYMAN:
    //             return 7;
    //         case GRANDMASTER:
    //             return 8;
    //         default:
    //             return 9;
    //     }
    // }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
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

    public void setFarmland(List<Plot> farmland) {
        this.farmland = farmland;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Farmer)) return false;
        Farmer other = (Farmer) o;
        return super.equals(other)
                && Objects.equals(xp, other.xp)
                && Objects.equals(wealth, other.wealth)
                && Objects.equals(wealthRankAmongFriends,
                        other.wealthRankAmongFriends)
                && Objects.deepEquals(farmland, other.farmland);
    }
}
