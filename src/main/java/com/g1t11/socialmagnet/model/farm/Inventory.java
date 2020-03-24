package com.g1t11.socialmagnet.model.farm;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Crop, Integer> inv = new HashMap<>();

    public void addCrop(Crop crop, int quantity) {
        inv.put(crop, inv.getOrDefault(crop, 0) + quantity);
    }

    public boolean removeCrop(Crop crop, int quantity) {
        if (!inv.containsKey(crop)) return false;
        int newQuantity = inv.get(crop) - quantity;
        if (newQuantity < 0) return false;
        if (newQuantity == 0) inv.remove(crop);
        inv.put(crop, newQuantity);
        return true;
    } 

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Inventory)) return false;
        Inventory other = (Inventory) o;
        return inv.equals(other.inv);
    }
}
