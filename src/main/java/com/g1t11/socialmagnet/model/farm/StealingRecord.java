package com.g1t11.socialmagnet.model.farm;

import com.g1t11.socialmagnet.util.TextUtils;

/**
 * Represent a stealing record.
 */
public class StealingRecord {
    private String cropName;

    private int quantity;

    private int totalXpGained;

    private int totalGoldGained;

    public StealingRecord() {}

    /**
     * Creates a stealing record with specified crop, quantity, total
     * experience point and gold gained.
     * @param cropName The name of the crop.
     * @param quantity The quantity of produced stolen.
     * @param totalXPGained The total experience point gained.
     * @param totalGoldGained The total gold gained.
     */
    public StealingRecord(String cropName, int quantity,
            int totalXpGained, int totalGoldGained) {
        this.cropName = cropName;
        this.quantity = quantity;
        this.totalXpGained = totalXpGained;
        this.totalGoldGained = totalGoldGained;
    }

    /**
     * Creates a stealing record with specified crop and quantity.
     * @param crop The name of the crop.
     * @param quantity The quantity of produced stolen.
     */
    public StealingRecord(Crop crop, int quantity) {
        this(crop.getName(), quantity, quantity * crop.getPerUnitXp(),
                 quantity * crop.getSalePrice());
    }

    /**
     * Gets the name of the crop.
     * @return The name of the crop.
     */
    public String getCropName() {
        return cropName;
    }

    /**
     * Sets crop name of stealing record.
     * @param cropName Crop name.
     */
    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity of stealing record.
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets total experience points gained.
     * @return The total experience points gained.
     */
    public int getTotalXpGained() {
        return totalXpGained;
    }

    /**
     * Sets total experience points gained.
     * @param totalXpGained The total experience points gained.
     */
    public void setTotalXpGained(int totalXpGained) {
        this.totalXpGained = totalXpGained;
    }

    /**
     * Gets the total gold gained.
     * @return The total gold gained.
     */
    public int getTotalGoldGained() {
        return totalGoldGained;
    }

    /**
     * Sets total gold gained.
     * @param totalGoldGained Total gold gained.
     */
    public void setTotalGoldGained(int totalGoldGained) {
        this.totalGoldGained = totalGoldGained;
    }

    @Override
    public String toString() {
        return String.format("%s for %d XP and %d gold",
                TextUtils.countedWord(quantity, cropName, cropName + "s"),
                totalXpGained,
                totalGoldGained);
    }
}
