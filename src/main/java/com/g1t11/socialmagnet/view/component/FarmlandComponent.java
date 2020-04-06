package com.g1t11.socialmagnet.view.component;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Plot;

/**
 * This is a render component for Farmland.
 */
public class FarmlandComponent implements Component {
    List<PlotComponent> plotComps = new ArrayList<>();

    /**
     * Sets the list of plot components for rendering.
     * @param plots The list of plot components.
     */
    public void setPlots(List<Plot> plots) {
        plotComps.clear();
        int index = 1;
        for (Plot plot : plots) {
            plotComps.add(new PlotComponent(
                    plot, index++, getMaxCropNameLength(plots)));
        }
    }

    /**
     * A method to render out the components for Farmland.
     */
    @Override
    public void render() {
        for (PlotComponent plotComp : plotComps) {
            plotComp.render();
        }
    }

    /**
     * A method to calulate the amount of spaces required for formatting.
     * @param plots The list of plots.
     * @return The amount of spaces required for formatting.
     */
    private int getMaxCropNameLength(List<Plot> plots) {
        int maxLength = 0;
        for (Plot plot : plots) {
            Crop crop = plot.getCrop();
            if (crop == null) {
                continue;
            }
            if (crop.getName().length() > maxLength) {
                maxLength = crop.getName().length();
            }
        }
        return maxLength;
    }
}
