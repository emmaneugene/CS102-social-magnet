package com.g1t11.socialmagnet.model.farm;

/**
 * InventoryItem
 * - crop : Crop
 * - quantity : int
 */
public class InventoryItem {
    private Crop crop = null;
    
    private int quantity = 0;

    public InventoryItem(Crop crop, int quantity) {
        this.crop = crop;
        this.quantity = quantity;
    }

    public Crop getCrop() {
        return crop;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void subtractQuantity(int quantity) {
        this.quantity -= quantity;
    } 
}
