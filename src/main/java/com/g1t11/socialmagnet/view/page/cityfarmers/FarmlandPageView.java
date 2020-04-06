package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.TextUtils;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.component.FarmlandComponent;
import com.g1t11.socialmagnet.view.component.InventoryComponent;

/**
 * This is a page view for City Farmers' Farmland page.
 */
public class FarmlandPageView extends CityFarmersPageView {
    FarmlandComponent farmComp = new FarmlandComponent();

    InventoryComponent invComp;

    /**
     * Creates a Farmland page view.
     */
    public FarmlandPageView() {
        super("My Farmland");
    }

    /**
     * Sets the specified plots for the Farmland.
     * @param plots The list of plots.
     */
    public void setPlots(List<Plot> plots) {
        farmComp.setPlots(plots);
    }

    /**
     * Sets the names for the crops in inventory.
     * @param invCropNames The list of names of crops in inventory.
     */
    public void setInventoryCropNames(List<String> invCropNames) {
        invComp = new InventoryComponent(invCropNames);
    }


    @Override
    public void display() {
        super.display();

        System.out.printf("You have %s of land.\n",
            TextUtils.countedWord(getMe().getMaxPlotCount(), "plot", "plots"));

        farmComp.render();
        System.out.println();
    }

    /**
     * A method to display "Select the crop" and renders it.
     */
    public void displayInvMenu() {
        System.out.println();
        System.out.println("Select the crop:");
        invComp.render();
    }

    @Override
    public void showMainPrompt() {
        showPrompt(Painter.paintf(
                "[[{M}]]ain | City [[{F}]]armers | [[{P}]]lant"
                        + " | [[{C}]]lear | [[{H}]]arvest",
                Color.YELLOW));
        setInputColor(Color.YELLOW);
    }

    /**
     * A method to prompt user to select a choice to plant crop from inventory.
     */
    public void showGetCropPrompt() {
        showPrompt(Painter.paintf(
                "[[{M}]]ain | City [[{F}]]armers | Select Choice",
                Color.YELLOW));
        setInputColor(Color.YELLOW);
    }

    /**
     * A method to prompt user to confirm the clearing of healthy crop.
     */
    public void showConfirmClearPrompt() {
        showPrompt("Confirm clearing healthy crop? (Y/n)");
    }
}
