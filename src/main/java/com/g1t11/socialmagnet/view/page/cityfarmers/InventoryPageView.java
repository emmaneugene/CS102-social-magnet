package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.Map;

import com.g1t11.socialmagnet.util.TextUtils;

public class InventoryPageView extends CityFarmersPageView {
    Map<String, Integer> invCrops;

    public InventoryPageView(Map<String, Integer> invCrops) {
        super("My Inventory");
        this.invCrops = invCrops;
    }

    @Override
    public void display() {
        super.display();

        System.out.println("My Seeds:");
        int index = 1;
        for (String key : invCrops.keySet()) {
            int quantity = invCrops.get(key);
            System.out.printf("%d. %s of %s\n",
                index++, TextUtils.countedWord(quantity, "Bag", "Bags"), key
            );
        }
        System.out.println();
    }
}