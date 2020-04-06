package com.g1t11.socialmagnet.model.farm;

import com.g1t11.socialmagnet.util.TextUtils;

/**
 * Represent a stealing record.
 */
public class StealingRecord {
    private String cropName;

    private int quantity;

    private int totalXPGained;

    private int totalGoldGained;

    /**
     * Creates a stealing record with specified crop, quantity, total
     * experience point and gold gained.
     * @param cropName The name of the crop.
     * @param quantity The quantity of produced stolen.
     * @param totalXPGained The total experience point gained.
     * @param totalGoldGained The total gold gained.
     */
    public StealingRecord(String cropName, int quantity,
            int totalXPGained, int totalGoldGained) {
        this.cropName = cropName;
        this.quantity = quantity;
        this.totalXPGained = totalXPGained;
        this.totalGoldGained = totalGoldGained;
    }

    /**
     * Creates a stealing record with specified crop and quantity.
     * @param crop The name of the crop.
     * @param quantity The quantity of produced stolen.
     */
    public StealingRecord(Crop crop, int quantity) {
        this(crop.getName(), quantity, quantity * crop.getPerUnitXP(),
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
     * Gets the quanity of the stolen produce.
     * @return The quanity of the stolen produce.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the total experienced point gained.
     * @return The total experienced point gained.
     */
    public int getTotalXPGained() {
        return totalXPGained;
    }

    /**
     * Gets the total gold gained.
     * @return The total gold gained.
     */
    public int getTotalGoldGained() {
        return totalGoldGained;
    }

    /**
     * Returns the details of the stealing record as a string.
     * @return The string of details of the stealing record. 
     */
    @Override
    public String toString() {
        return String.format("%s for %d XP and %d gold",
                TextUtils.countedWord(quantity, cropName, cropName + "s"),
                totalXPGained,
                totalGoldGained);
    }
}
