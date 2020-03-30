package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.data.StoreDAO;
import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.cityfarmers.SendGiftPageView;

public class SendGiftController extends CityFarmersController {
    StoreDAO storeDAO = new StoreDAO(database());

    List<Crop> crops;

    public SendGiftController(Navigator nav, Farmer me) {
        super(nav, me);
        crops = storeDAO.getStoreItems();
        view = new SendGiftPageView(crops);
    }

    @Override
    public void handleInput() {
        input = new PromptInput(Painter.paintf(
            "[[{M}]]ain | Select choice",
            Painter.Color.YELLOW
        ));

        String choice = input.nextLine();
        if (choice.length() == 0) {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
        if (choice.equals("M")) {

        }
        handleCropSelection(choice);
    }

    private void handleCropSelection(String choice) {
        try {
            int index = Integer.parseInt(choice);
            if (index <= 0 || index > crops.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
                return;
            }
            Crop toSend = crops.get(index - 1);
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Use <id> to select a gift.", Painter.Color.RED));
        }
    }
}