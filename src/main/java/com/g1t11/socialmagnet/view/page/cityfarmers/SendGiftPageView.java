package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.util.Painter;

public class SendGiftPageView extends CityFarmersPageView {
    List<Crop> crops;

    public SendGiftPageView(List<Crop> crops) {
        super("Send a Gift");
        this.crops = crops;
    }

    @Override
    public void display() {
        super.display();

        System.out.println("Gifts Available:");

        int index = 1;
        for (Crop crop : crops) {
            System.out.printf(Painter.paintf("[{%d.}] 1 Bag of %s Seeds\n", Painter.Color.YELLOW),
                index++, crop.getName());
        }
        System.out.println();
    }
}