package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * This is a render component for Plot.
 */
public class PlotComponent implements Component {
    private Plot plot;

    private int index;

    private int maxCropNameLength;

    /**
     * Creates a plot component with specific plot, index and amount of spaces 
     * for formatting.
     * @param plot The plot.
     * @param index The index of plot.
     * @param maxCropNameLength The amount of spaces required for formatting.
     */
    public PlotComponent(Plot plot, int index, int maxCropNameLength) {
        this.plot = plot;
        this.index = index;
        this.maxCropNameLength = maxCropNameLength;
    }

    /**
     * A method used to render out the component for Plot.
     */
    @Override
    public void render() {
        System.out.printf(Painter.paint("%d. ", Color.YELLOW), index);
        if (plot.getCrop() == null) {
            System.out.println("<empty>");
            return;
        }
        // Left pad crop name
        System.out.printf("%-" + (maxCropNameLength + 2) + "s",
                plot.getCrop().getName());
        if (plot.isWilted()) {
            renderWilted();
        } else {
            renderProgressBar();
            System.out.printf(" %d%%", plot.getPercentProgress());
        }
        System.out.println();
    }

    /**
     * A method used to render out wilted plot.
     */
    private void renderWilted() {
        System.out.print(Painter.paintf("[  [{wilted}]  ]", Color.RED));
    }

    private static final String progressBarTemplate
            = Painter.paintf("[[{%s}]%s]", Color.GREEN);

    /**
     * A method to render the progress bar of the plot.
     */
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
