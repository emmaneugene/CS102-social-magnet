package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

public class SendGiftPageView extends CityFarmersPageView {
    private List<Crop> crops;

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
            System.out.printf(Painter.paintf(
                    "[{%d.}] 1 Bag of %s Seeds\n", Color.YELLOW),
                    index++, crop.getName());
        }
        System.out.println();
    }

    @Override
    public void showMainPrompt() {
        showPrompt(Painter.paintf(
                "[[{M}]]ain | Select choice", Color.YELLOW));
        setInputColor(Color.YELLOW);
    }

    public void showSendToPrompt() {
        showPrompt("Send to");
        setInputColor(Color.BLUE);
    }
}