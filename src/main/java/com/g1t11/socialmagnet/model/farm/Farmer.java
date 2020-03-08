package com.g1t11.socialmagnet.model.farm;

import com.g1t11.socialmagnet.model.social.User;
import java.util.ArrayList;

/**
 * Farmer extends User
 * - rank : String
 * - exp : int
 * - wealth : int
 * - farmland : ArrayList<Plot>
 * - inventory : ArrayList<InventoryItem>
 * - stolenCrops : ArrayList<Stealing>
 */
public class Farmer extends User {
    private String rank = null;

    private int exp = 0;

    private int wealth = 50;

    private ArrayList<Plot> farmland = null;

    private ArrayList<InventoryItem> inventory = null;

    private ArrayList<Stealing> stolenCrops = null;

    public Farmer() {}

    public Farmer(String username, String fullName) {
        super(username, fullName);
    }

    public String getRank() {
        return rank;
    }

    public String setRank() {
        //TODO: set farmer's rank based on exp, whenever the exp changes
        return null;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    // convenience method to add exp
    public void addExp(int exp_earned) {
        exp += exp_earned;
    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public ArrayList<Plot> getFarmland() {
        return farmland;
    }

    public void setFarmland(ArrayList<Plot> farmland) {
        this.farmland = farmland;
    }

    public void addPlot(Plot plot) {
        farmland.add(plot);
    }

    public ArrayList<InventoryItem> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<InventoryItem> inventory) {
        this.inventory = inventory;
    }

    public void addInventoryItem(InventoryItem item) {
        inventory.add(item);
    }

    public ArrayList<Stealing> getStolenCrops() {
        return stolenCrops;
    }

    public void setStolenCrops(ArrayList<Stealing> stolenCrops) {
        this.stolenCrops = stolenCrops;
    }

    public void addStolenCrop(Stealing stolenCrop) {
        stolenCrops.add(stolenCrop);
    }
}
