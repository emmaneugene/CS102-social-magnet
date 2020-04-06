package com.g1t11.socialmagnetservice.model.farm;

import com.g1t11.socialmagnetservice.util.TextUtils;

public class StealingRecord {
    private String cropName;

    private int quantity;

    private int totalXpGained;

    private int totalGoldGained;

    public StealingRecord() {}

    public StealingRecord(String cropName, int quantity,
            int totalXpGained, int totalGoldGained) {
        this.cropName = cropName;
        this.quantity = quantity;
        this.totalXpGained = totalXpGained;
        this.totalGoldGained = totalGoldGained;
    }

    public StealingRecord(Crop crop, int quantity) {
        this(crop.getName(), quantity, quantity * crop.getPerUnitXp(),
                 quantity * crop.getSalePrice());
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalXpGained() {
        return totalXpGained;
    }

    public void setTotalXpGained(int totalXpGained) {
        this.totalXpGained = totalXpGained;
    }

    public int getTotalGoldGained() {
        return totalGoldGained;
    }

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
