package com.g1t11.socialmagnet.model.farm;

import java.util.Objects;

public class Crop {
    private String name;

    private int cost;

    private int minutesToHarvest;

    private int perUnitXP;

    private Integer yield;

    private int minYield;

    private int maxYield;

    private int salePrice;

    public Crop(String name, int cost, int minutesToHarvest, int perUnitXP,
            Integer yield, int minYield, int maxYield, int salePrice) {
        this.name = name;
        this.cost = cost;
        this.minutesToHarvest = minutesToHarvest;
        this.perUnitXP = perUnitXP;
        this.yield = yield;
        this.minYield = minYield;
        this.maxYield = maxYield;
        this.salePrice = salePrice;
    }

    // Empty Crop
    public Crop() {
        this.name = "empty crop";
        this.cost = 0;
        this.minutesToHarvest = 0;
        this.perUnitXP = 0;
        this.yield = 0;
        this.minYield = 0;
        this.maxYield = 0;
        this.salePrice = 0;
    }

    public Crop(String name, int cost, int minutesToHarvest, int perUnitXP,
            int minYield, int maxYield, int salePrice) {
        this(name, cost, minutesToHarvest, perUnitXP, null,
                minYield, maxYield, salePrice);
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getMinutesToHarvest() {
        return minutesToHarvest;
    }

    public int getPerUnitXP() {
        return perUnitXP;
    }

    public int getMinYield() {
        return minYield;
    }

    public int getMaxYield() {
        return maxYield;
    }

    public int getRandomYield() {
        if (yield == null) {
            yield = minYield + (int) Math.random() * (maxYield - minYield);
        }
        return yield;
    }

    public int getSalePrice() {
        return salePrice;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Crop)) return false;
        Crop other = (Crop) o;
        return Objects.equals(name, other.name)
                && Objects.equals(cost, other.cost)
                && Objects.equals(minutesToHarvest, other.minutesToHarvest)
                && Objects.equals(perUnitXP, other.perUnitXP)
                && Objects.equals(minYield, other.minYield)
                && Objects.equals(maxYield, other.maxYield)
                && Objects.equals(salePrice, other.salePrice);
    }
}
