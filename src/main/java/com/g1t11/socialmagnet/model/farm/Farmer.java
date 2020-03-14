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
    private int XP = 0;

    /** 
     *  By default, new farmers start with 50 gold 
     */ 
    private int wealth = 50;

    private int wealthRank = 0;

    private List<Plot> farmland = new ArrayList<>();

    private List<InventoryItem> inventory = new ArrayList<>();

    public Farmer(String username, String fullname, int XP, int wealth, int wealthRank) {
        super(username, fullname);
        this.XP = XP;
        this.wealth = wealth;
        this.wealthRank = wealthRank;
    }

    public Farmer(String username, String fullname) {
        super(username, fullname);
    }

    public Farmer() {}

    public String getRank() {
        if (XP < 1000) {
            return "Novice";
        }
        if (XP < 2500) {
            return "Apprentice";
        }
        if (XP < 5000) {
            return "Journeyman";
        }
        if (XP < 12000) {
            return "Grandmaster";
        }
        return "Legendary";
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

    public int getWealthRank() {
        return wealthRank;
    }

    public void setWealthRank(int wealthRank) {
        this.wealthRank = wealthRank;
    }

    public List<Plot> getFarmland() {
        return farmland;
    }

    public List<InventoryItem> getInventory() {
        return inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Farmer)) return false;
        Farmer other = (Farmer) o;
        return super.equals(other)
        && Objects.equals(XP, other.XP)
        && Objects.equals(wealth, other.wealth)
        && Objects.equals(wealthRank, other.wealthRank)
        && Objects.deepEquals(farmland, other.farmland)
        && Objects.deepEquals(inventory, other.inventory);
    }
}
