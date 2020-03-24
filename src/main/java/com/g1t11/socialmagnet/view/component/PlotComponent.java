package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.util.Painter;

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
        System.out.printf(Painter.paint("%d. ", Painter.Color.YELLOW), index);
        if (plot.getCrop() == null) {
            System.out.println("<empty>");
            return;
        }
        // Left pad crop name
        System.out.printf("%-" + (maxCropNameLength + 2) + "s", plot.getCrop().getName());
        renderProgressBar();
        System.out.printf(" %d%%\n", plot.getPercentProgress());
    }

    private static final String progressBarTemplate 
        = Painter.paintf("[[{%s}]%s]", Painter.Color.GREEN);

    private void renderProgressBar() {
        int fillCount = plot.getPercentProgress() / 10;
        StringBuilder filledBuilder = new StringBuilder();
        for (int i = 0; i < fillCount; i++) {
            filledBuilder.append("#");
        }
        StringBuilder unfilledBuilder = new StringBuilder();
        for (int i = 10; i > fillCount; i--) {
            unfilledBuilder.append("-");
        }
        System.out.printf(progressBarTemplate, filledBuilder, unfilledBuilder);
    }
}