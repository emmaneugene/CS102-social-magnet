package com.g1t11.socialmagnet.model.farm;

import java.util.Objects;

/**
 * Represents a crop.
 */
public class Crop {
    private String name;

    private int cost;

    private int minutesToHarvest;

    private int perUnitXp;

    private Integer yield;

    private int minYield;

    private int maxYield;

    private int salePrice;

    /**
     * Creates a crop with specifed parameters including actual yield.
     * @param name The name of crop.
     * @param cost The cost of crop.
     * @param minutesToHarvest The time for the crop to grow to maturity.
     * @param perUnitXP The experience points per unit of crop.
     * @param yield The actual yield of crop will be produced.
     * @param minYield The minimum yield of crop can be produced.
     * @param maxYield The maximum yield of crop can be produced.
     * @param salePrice The sale price of crop.
     */
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

    /**
     * Creates a empty crop
     */
    public Crop() {}

    /**
     * Creates a crop with specifed parameters without actual yield.
     * @param name The name of crop.
     * @param cost The cost of crop.
     * @param minutesToHarvest The time for the crop to grow to maturity.
     * @param perUnitXP The experience points per unit of crop.
     * @param minYield The minimum yield of crop can be produced.
     * @param maxYield The maximum yield of crop can be produced.
     * @param salePrice The sale price of crop.
     */
    public Crop(String name, int cost, int minutesToHarvest, int perUnitXP,
            int minYield, int maxYield, int salePrice) {
        this(name, cost, minutesToHarvest, perUnitXP, null,
                minYield, maxYield, salePrice);
    }

    /**
     * Gets the name of crop.
     * @return The name of crop.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     * @param name Name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets cost.
     * @return Cost.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets cost.
     * @param cost Cost.
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Gets minutes to harvest crop.
     * @return Minutes for crop to harvest.
     */
    public int getMinutesToHarvest() {
        return minutesToHarvest;
    }

    /**
     * Sets minutes to harvest crop.
     */
    public void setMinutesToHarvest(int minutesToHarvest) {
        this.minutesToHarvest = minutesToHarvest;
    }

    /**
     * Gets per unit experience points.
     * @return Experience points per unit.
     */
    public int getPerUnitXp() {
        return perUnitXp;
    }

    /**
     * Sets per unit experience points.
     */
    public void setPerUnitXp(int perUnitXp) {
        this.perUnitXp = perUnitXp;
    }

    /**
     * Get yield.
     * @return Yield.
     */
    public Integer getYield() {
        return yield;
    }

    /**
     * Set yield.
     * @param yield Yield.
     */
    public void setYield(Integer yield) {
        this.yield = yield;
    }

    /**
     * Gets the minimum yield of crop.
     * @return The minimum yield of crop.
     */
    public int getMinYield() {
        return minYield;
    }

    /**
     * Sets minimum yield.
     * @param minYield Minimum yield.
     */
    public void setMinYield(int minYield) {
        this.minYield = minYield;
    }

    /**
     * Gets max yield.
     * @return Max yield.
     */
    public int getMaxYield() {
        return maxYield;
    }

    /**
     * Sets max yield.
     * @param maxYield Max yield.
     */
    public void setMaxYield(int maxYield) {
        this.maxYield = maxYield;
    }

    /**
     * Gets the sale price of the crop.
     * @return The sale price of the crop.
     */
    public int getSalePrice() {
        return salePrice;
    }

    /**
     * Sets sale price.
     * @param salePrice Sale price.
     */
    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Compares the specified object with this crop for equality.
     * It returns true if and only if specified object is a crop and both crops
     * have the same name, cost, time for the crop to grow to maturity,
     * minimum yield, maximum yield and sale price.
     */
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
