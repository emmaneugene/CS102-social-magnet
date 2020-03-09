package com.g1t11.socialmagnet.model.farm;

import com.g1t11.socialmagnet.model.social.User;
import java.util.ArrayList;

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

    private ArrayList<Plot> farmland = null;

    private ArrayList<InventoryItem> inventory = null;

    /**
     * Constructor for a brand new Farmer object
     */
    public Farmer(String username, String fullName) {
        super(username, fullName);
    }

    /**
     * Constructor for an existing Farmer (data from database) 
     */
    public Farmer(String username, String fullName, int XP, int wealth, ArrayList<Plot> farmland, ArrayList<InventoryItem> inventory) {
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
    public void addXP(int XP_earned) {
        XP += XP_earned;
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

    public ArrayList<Plot> getFarmland() {
        return farmland;
    }

    public void addPlot(Plot plot) {
        farmland.add(plot);
    }

    public ArrayList<InventoryItem> getInventory() {
        return inventory;
    }


    public void addInventoryItem(InventoryItem item) {
        inventory.add(item);
    }
}
