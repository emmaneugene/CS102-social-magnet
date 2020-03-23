package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.model.farm.Plot;

public class PlotComponent implements Component {
    private Plot plot;

    private int index;

    private int maxCropNameLength;

    public PlotComponent(Plot plot, int index, int maxCropNameLength) {
        this.plot = plot;
        this.index = index;
        this.maxCropNameLength = maxCropNameLength;
    }

    @Override
    public void render() {
        System.out.printf("%d. ", index);
        if (plot.getCrop() == null) {
            System.out.println("<empty>");
            return;
        }
        // Left pad crop name
        System.out.printf("%-" + (maxCropNameLength + 2) + "s", plot.getCrop().getName());
        renderProgressBar();
        System.out.printf(" %d%%\n", plot.getPercentProgress());
    }

    private void renderProgressBar() {
        System.out.print("[");
        int fillCount = plot.getPercentProgress() / 10;
        for (int i = 0; i < fillCount; i++) {
            System.out.print("#");
        }
        for (int i = 10; i > fillCount; i--) {
            System.out.print("-");
        }
        System.out.print("]");
    }
}