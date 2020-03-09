package com.g1t11.socialmagnet.model.farm;

import java.util.ArrayList;
import java.util.List;

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

    private List<Plot> farmland = new ArrayList<>();

    private List<InventoryItem> inventory = new ArrayList<>();

    /**
     * Constructor for a brand new Farmer object
     */
    public Farmer(String username, String fullName) {
        super(username, fullName);
    }

    /**
     * Constructor for an existing Farmer (data from database) 
     */
    public Farmer(String username, String fullName, int XP, int wealth, List<Plot> farmland, List<InventoryItem> inventory) {
        super(username, fullName);
        this.XP = XP;
        this.wealth = wealth;
        this.farmland = farmland;
        this.inventory = inventory;
    }

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

    public List<Plot> getFarmland() {
        return farmland;
    }

    public void addPlot(Plot plot) {
        farmland.add(plot);
    }

    public List<InventoryItem> getInventory() {
        return inventory;
    }

    public void addInventoryItem(InventoryItem item) {
        inventory.add(item);
    }
}
