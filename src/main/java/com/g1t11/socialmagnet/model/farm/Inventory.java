package com.g1t11.socialmagnet.model.farm;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Inventory {
    // Keep the inventory sorted by crop names
    private Map<Crop, Integer> inv = new TreeMap<>(new Comparator<Crop>() {
        public int compare(Crop c1, Crop c2) {
            return c1.getName().compareTo(c2.getName());
        }
    });

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

    public List<Crop> availableCrops() {
        return new ArrayList<Crop>(inv.keySet());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Inventory)) return false;
        Inventory other = (Inventory) o;
        return inv.equals(other.inv);
    }
}
