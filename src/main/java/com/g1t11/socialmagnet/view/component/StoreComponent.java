package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * This is a render component for Store.
 */
public class StoreComponent implements Component {
    private Crop crop;
    private int index;
    private int maxCropNameLength;

    /**
     * Creates a store component with specified crop, index and amount of spaces 
     * for formatting.
     * @param crop The crop.
     * @param index The index of crop.
     * @param maxCropNameLength The amount of spaces required for formatting.
     */
    public StoreComponent(Crop crop, int index, int maxCropNameLength) {
        this.crop = crop;
        this.index = index;
        this.maxCropNameLength = maxCropNameLength;
    }

    /**
     * A method used to render out Store.
     */
    @Override
    public void render() {
        System.out.printf(Painter.paint("%d. ", Color.YELLOW), index);

        // Left pad crop name
        System.out.printf("%-" + (maxCropNameLength + 2) + "s", crop.getName());

        System.out.printf("- %d gold \r\n", crop.getCost());
        System.out.printf("   Harvest in: %d mins \r\n",
                crop.getMinutesToHarvest());
        System.out.printf("   XP Gained: %d", crop.getPerUnitXP());
        System.out.println();
    }
}
