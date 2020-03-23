package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.util.TextUtils;
import com.g1t11.socialmagnet.view.component.PlotComponent;

public class FarmlandPageView extends CityFarmersPageView {
    Farmer me;

    List<PlotComponent> plotComps = new ArrayList<>();
    public FarmlandPageView(Farmer me) {
        super(me, "My Farmland");
        this.me = me;
    }

    public void setPlotComps(List<Plot> plots) {
        plotComps.clear();
        int index = 1;
        int maxCropNameLength = getMaxCropNameLength(plots);
        for (Plot plot : plots) {
            plotComps.add(new PlotComponent(plot, index++, maxCropNameLength));
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

    @Override
    public void display() {
        super.display();
        System.out.println();

        System.out.printf("You have %s of land.\n", 
            TextUtils.countedWord(me.getMaxPlotCount(), "plot", "plots"));

        for (PlotComponent plotComp : plotComps) {
            plotComp.render();
        }
    }
}