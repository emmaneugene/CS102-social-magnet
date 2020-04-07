package com.g1t11.socialmagnet.model.farm;

import java.util.Objects;

public class Crop {
    private String name;

    private int cost;

    private int minutesToHarvest;

    private int perUnitXp;

    private Integer yield;

    private int minYield;

    private int maxYield;

    private int salePrice;

    public Crop(String name, int cost, int minutesToHarvest, int perUnitXP,
            Integer yield, int minYield, int maxYield, int salePrice) {
        this.name = name;
        this.cost = cost;
        this.minutesToHarvest = minutesToHarvest;
        this.perUnitXp = perUnitXP;
        this.yield = yield;
        this.minYield = minYield;
        this.maxYield = maxYield;
        this.salePrice = salePrice;
    }

    public Crop() {}

    public Crop(String name, int cost, int minutesToHarvest, int perUnitXP,
            int minYield, int maxYield, int salePrice) {
        this(name, cost, minutesToHarvest, perUnitXP, null,
                minYield, maxYield, salePrice);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getMinutesToHarvest() {
        return minutesToHarvest;
    }

    public void setMinutesToHarvest(int minutesToHarvest) {
        this.minutesToHarvest = minutesToHarvest;
    }

    public int getPerUnitXp() {
        return perUnitXp;
    }

    public void setPerUnitXp(int perUnitXp) {
        this.perUnitXp = perUnitXp;
    }

    public Integer getYield() {
        return yield;
    }

    public void setYield(Integer yield) {
        this.yield = yield;
    }

    public int getMinYield() {
        return minYield;
    }

    public void setMinYield(int minYield) {
        this.minYield = minYield;
    }

    public int getMaxYield() {
        return maxYield;
    }

    public void setMaxYield(int maxYield) {
        this.maxYield = maxYield;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
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
                && Objects.equals(perUnitXp, other.perUnitXp)
                && Objects.equals(minYield, other.minYield)
                && Objects.equals(maxYield, other.maxYield)
                && Objects.equals(salePrice, other.salePrice);
    }
}
