package com.g1t11.socialmagnetservice.model.farm;

import com.g1t11.socialmagnetservice.util.TextUtils;

public class StealingRecord {
    private String cropName;

    private int quantity;

    private int totalXPGained;

    private int totalGoldGained;

    public StealingRecord() {}

    public StealingRecord(String cropName, int quantity,
            int totalXPGained, int totalGoldGained) {
        this.cropName = cropName;
        this.quantity = quantity;
        this.totalXPGained = totalXPGained;
        this.totalGoldGained = totalGoldGained;
    }

    public StealingRecord(Crop crop, int quantity) {
        this(crop.getName(), quantity, quantity * crop.getPerUnitXP(),
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

    public int getTotalXPGained() {
        return totalXPGained;
    }

    public void setTotalXPGained(int totalXPGained) {
        this.totalXPGained = totalXPGained;
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
                totalXPGained,
                totalGoldGained);
    }
}
