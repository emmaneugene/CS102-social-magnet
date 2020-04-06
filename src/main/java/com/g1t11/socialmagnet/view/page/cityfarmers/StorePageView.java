package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.component.StoreComponent;

/**
 * This is a page view for City Farmers' store page view.
 */
public class StorePageView extends CityFarmersPageView {
    List<StoreComponent> cropComps = new ArrayList<>();

    /**
     * Creates a Store page view.
     */
    public StorePageView() {
        super("My Store");
    }

    /**
     * Sets the crops that is available in the Store.
     * @param crops The list of crops that is available in Store.
     */
    public void setCrops(List<Crop> crops) {
        cropComps.clear();
        int index = 1;
        int maxCropNameLength = getMaxCropNameLength(crops);
        for (Crop crop : crops) {
            cropComps.add(new StoreComponent(crop, index++, maxCropNameLength));
        }
    }

    @Override
    public void display() {
        super.display();

        System.out.println("Seeds available:");

        for (StoreComponent cropComp : cropComps) {
            cropComp.render();
        }
        System.out.println();
    }

    /**
     * Gets the maximum length of the crop name that is available in Store.
     * @param crops The list of crops available in the Store.
     * @return The maximum length of the crop name.
     */
    private int getMaxCropNameLength(List<Crop> crops) {
        int maxLength = 0;
        for (Crop crop : crops) {
            if (crop == null) continue;
            if (crop.getName().length() > maxLength) {
                maxLength = crop.getName().length();
            }
        }
        return maxLength;
    }

    @Override
    public void showMainPrompt() {
        showPrompt(Painter.paintf(
                "[[{M}]]ain | City [[{F}]]armers | Select Choice",
                Color.YELLOW));
        setInputColor(Color.YELLOW);
    }

    /**
     * A method to prompt user for the quantity of purchase.
     */
    public void showQuantityPrompt() {
        showPrompt("Enter quantity");
        setInputColor(Color.YELLOW);
    }
}
