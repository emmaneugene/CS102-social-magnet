package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.view.component.StoreComponent;

public class StorePageView extends CityFarmersPageView {
    List<StoreComponent> cropComps = new ArrayList<>();

    public StorePageView() {
        super("My Store");
    }

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
}