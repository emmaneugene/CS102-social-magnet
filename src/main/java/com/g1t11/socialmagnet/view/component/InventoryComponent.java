package com.g1t11.socialmagnet.view.component;

import java.util.List;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.component.Component;

/**
 * This is a render component for inventory.
 */
public class InventoryComponent implements Component {
    List<String> invCropNames;

    /**
     * Create an inventory component with the specified list of crop names.
     * @param invCropNames The list of crops name.
     */
    public InventoryComponent(List<String> invCropNames) {
        this.invCropNames = invCropNames;
    }

    /**
     * A method used to render out compenent for Inventory.
     */
    @Override
    public void render() {
        int index = 1;
        for (String invCropName : invCropNames) {
            System.out.printf(
                    Painter.paintf("[{%d.}] %s\n", Color.YELLOW),
                    index++, invCropName);
        }
    }
}
