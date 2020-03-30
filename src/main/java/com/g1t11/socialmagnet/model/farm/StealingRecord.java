package com.g1t11.socialmagnet.model.farm;

import com.g1t11.socialmagnet.util.TextUtils;

public class StealingRecord {
    private String cropName;

    private int quantity;

    private int totalXPGained;

    private int totalGoldGained;

    public StealingRecord(String cropName, int quantity, int totalXPGained, int totalGoldGained) {
        this.cropName = cropName;
        this.quantity = quantity;
        this.totalXPGained = totalXPGained;
        this.totalGoldGained = totalGoldGained;
    }

    public StealingRecord(Crop crop, int quantity) {
        this(crop.getName(), quantity, quantity * crop.getPerUnitXP(), quantity * crop.getSalePrice());
    }

    public String getCropName() {
        return cropName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalXPGained() {
        return totalXPGained;
    }

    public int getTotalGoldGained() {
        return totalGoldGained;
    }

    @Override
    public String toString() {
        return String.format("%s for %d XP and %d gold",
            TextUtils.countedWord(quantity, cropName, cropName + "s"),
            totalXPGained,
            totalGoldGained
        );
    }
}
