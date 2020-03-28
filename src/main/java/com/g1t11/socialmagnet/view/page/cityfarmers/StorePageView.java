package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.view.component.StoreComponent;

public class StorePageView extends CityFarmersPageView {
    List<StoreComponent> cropComps = new ArrayList<>();

    public StorePageView(Farmer me) {
        super(me, "My Store");
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
        System.out.println();

        System.out.printf("Seeds vailable: \r\n");

        for (StoreComponent cropComp : cropComps) {
            cropComp.render();
        }
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