package com.g1t11.socialmagnet.view.component;

import java.util.List;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.component.Component;

public class InventoryComponent implements Component {
    private List<String> invCropNames;

    public InventoryComponent(List<String> invCropNames) {
        this.invCropNames = invCropNames;
    }

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
