package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

public class StoreComponent implements Component {
    private Crop crop;

    private int index;

    private int maxCropNameLength;

    public StoreComponent(Crop crop, int index, int maxCropNameLength) {
        this.crop = crop;
        this.index = index;
        this.maxCropNameLength = maxCropNameLength;
    }

    @Override
    public void render() {
        System.out.printf(Painter.paint("%d. ", Color.YELLOW), index);

        // Left pad crop name
        System.out.printf("%-" + (maxCropNameLength + 2) + "s", crop.getName());

        System.out.printf("- %d gold \r\n", crop.getCost());
        System.out.printf("   Harvest in: %d mins \r\n",
                crop.getMinutesToHarvest());
        System.out.printf("   XP Gained: %d", crop.getPerUnitXp());
        System.out.println();
    }
}
