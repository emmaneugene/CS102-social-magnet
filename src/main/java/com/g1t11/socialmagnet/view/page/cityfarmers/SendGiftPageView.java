package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * This is a page view for City Farmers' Send Gift page.
 */
public class SendGiftPageView extends CityFarmersPageView {
    List<Crop> crops;

    /**
     * Creates a Send Gift page view with the list of crops available to gift.
     * @param crops The list of crops that is available for gifting.
     */
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

    /**
     * A method to prompt user for the friend to gift to.
     */
    public void showSendToPrompt() {
        showPrompt("Send to");
        setInputColor(Color.BLUE);
    }
}