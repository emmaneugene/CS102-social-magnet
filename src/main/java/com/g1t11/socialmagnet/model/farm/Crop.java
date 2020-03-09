package com.g1t11.socialmagnet.model.farm;

/**
 * Crop
 * - name : String
 * - cost : int
 * - time : int (in minutes)
 * - XP : int
 * - minYield : int
 * - maxYield : int
 * - salePrice : int
 */
public class Crop {
    private String name = null;

    private int cost = 0;

    private int time = 0;

    private int XP = 0;

    private int minYield = 0;

    private int maxYield = 0;

    private int salePrice = 0;

    public Crop(String name, int cost, int time, int XP, int minYield, int maxYield, int salePrice) {
        this.name = name;
        this.cost = cost;
        this.time = time;
        this.XP = XP;
        this.minYield = minYield;
        this.maxYield = maxYield;
        this.salePrice = salePrice;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getTime() {
        return time;
    }

    public int getXP() {
        return XP;
    }

    public int getMinYield() {
        return minYield;
    }

    public int getMaxYield() {
        return maxYield;
    }

    public int getSalePrice() {
        return salePrice;
    }
}
