package com.g1t11.socialmagnet.model.farm;

/**
 * Crop
 * - name : String
 * - seedCost : int
 * - growingTime : int (in minutes)
 * - exp : int
 * - minYield : int
 * - maxYield : int
 * - sellingPrice : int
 */
public class Crop {
    private String name;
    private int seedCost;
    private int growingTime;
    private int exp;
    private int minYield;
    private int maxYield;
    private int sellingPrice;

    public int getGrowingTime() {
        return growingTime;
    }
}
