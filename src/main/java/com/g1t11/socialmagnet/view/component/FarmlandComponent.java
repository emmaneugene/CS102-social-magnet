package com.g1t11.socialmagnet.view.component;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Plot;

public class FarmlandComponent implements Component {
    List<PlotComponent> plotComps = new ArrayList<>();

    public void setPlots(List<Plot> plots) {
        plotComps.clear();
        int index = 1;
        for (Plot plot : plots) {
            plotComps.add(new PlotComponent(plot, index++, getMaxCropNameLength(plots)));
        }
    }

    @Override
    public void render() {
        for (PlotComponent plotComp : plotComps) {
            plotComp.render();
        }
    }

    private int getMaxCropNameLength(List<Plot> plots) {
        int maxLength = 0;
        for (Plot plot : plots) {
            Crop crop = plot.getCrop();
            if (crop == null) continue;
            if (crop.getName().length() > maxLength) {
                maxLength = crop.getName().length();
            }
        }
        return maxLength;
    }
}