package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Inventory;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.view.component.Component;

public class InventoryComponent implements Component {
    Inventory inv;

    public InventoryComponent(Inventory inv) {
        this.inv = inv;
    }

    @Override
    public void render() {
        int index = 1;
        for (Crop crop : inv.availableCrops()) {
            System.out.printf(Painter.paintf("[{%d.}] %s\n", Painter.Color.YELLOW), 
                index++, crop.getName());
        }
    }
}