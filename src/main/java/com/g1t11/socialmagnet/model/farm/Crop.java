package com.g1t11.socialmagnet.model.farm;

import java.util.Objects;

/**
 * Represents a crop.
 */
public class Crop {
    private String name;

    private int cost;

    private int minutesToHarvest;

    private int perUnitXP;

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
        this.perUnitXP = perUnitXP;
        this.yield = yield;
        this.minYield = minYield;
        this.maxYield = maxYield;
        this.salePrice = salePrice;
    }

    /**
     * Creates an empty crop.
     */
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
     * Gets the cost of crop.
     * @return The cost of crop.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets the time for the crop to grow to maturity in minutes.
     * @return The time for the crop to grow to maturity in minutes.
     */
    public int getMinutesToHarvest() {
        return minutesToHarvest;
    }

    /**
     * Gets the experience points per unit of crop.
     * @return The experience points per unit of crop.
     */
    public int getPerUnitXP() {
        return perUnitXP;
    }

    /**
     * Gets the minimum yield of crop.
     * @return The minimum yield of crop.
     */
    public int getMinYield() {
        return minYield;
    }

    /**
     * Gets the maximum yield of crop. 
     * @return The maximum yield of crop.
     */
    public int getMaxYield() {
        return maxYield;
    }

    /**
     * Gets the random yield between the minimum and maximum yield.
     * The formula to calculate the yield is Min Yield + random number in the
     * range of 0 to (Max Yield - min Yield).
     * @return The random yield between the minimum and maximum yield.
     */
    public int getRandomYield() {
        if (yield == null) {
            yield = minYield + (int) Math.random() * (maxYield - minYield);
        }
        return yield;
    }

    /**
     * Gets the sale price of the crop.
     * @return The sale price of the crop.
     */
    public int getSalePrice() {
        return salePrice;
    }

    /**
     * Uses the hashcode of the name of crop as hashcode of crop.
     */
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
                && Objects.equals(perUnitXP, other.perUnitXP)
                && Objects.equals(minYield, other.minYield)
                && Objects.equals(maxYield, other.maxYield)
                && Objects.equals(salePrice, other.salePrice);
    }
}
