package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.TextUtils;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.component.FarmlandComponent;
import com.g1t11.socialmagnet.view.component.InventoryComponent;

public class FarmlandPageView extends CityFarmersPageView {
    FarmlandComponent farmComp = new FarmlandComponent();

    InventoryComponent invComp;

    public FarmlandPageView() {
        super("My Farmland");
    }

    public void setPlots(List<Plot> plots) {
        farmComp.setPlots(plots);
    }

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

    public void showGetCropPrompt() {
        showPrompt(Painter.paintf(
                "[[{M}]]ain | City [[{F}]]armers | Select Choice", Color.YELLOW));
        setInputColor(Color.YELLOW);
    }

    public void showConfirmClearPrompt() {
        showPrompt("Confirm clearing healthy crop? (Y/n)");
    }
}
