package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.data.StoreDAO;
import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.util.TextUtils;
import com.g1t11.socialmagnet.view.page.cityfarmers.StorePageView;

public class StoreController extends CityFarmersController {
    StoreDAO storeDAO = new StoreDAO(nav.database());

    Farmer me;

    List<Crop> storeItem = new ArrayList<>();

    public StoreController(Navigator nav, Farmer me) {
        super(nav, me);
        this.me = me;
        view = new StorePageView();
    }

    @Override
    public void updateView() {
        super.updateView();
        storeItem = storeDAO.getStoreItems();
        ((StorePageView) view).setCrops(storeItem);
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf(
            "[[{M}]]ain | City [[{F}]]armers | Select Choice", 
            Painter.Color.YELLOW
        ));
        
        String choice = input.nextLine();
        if (choice.length() == 0) {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        } else if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
        } else if (choice.equals("F")) {
            nav.popTo(CityFarmersMainMenuController.class);
        } else if (choice.matches("-?\\d+")) {
            handleBuyItem(choice);
        } else {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }

    private void handleBuyItem(String choice) {
        try {
            int index = Integer.parseInt(choice);
            if (index <= 0 || index > storeItem.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
            } else {
                handleBuyQuantity(index);
            }
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Use <id> to select a crop to purchase.", Painter.Color.RED));
        }
    }

    private void handleBuyQuantity(int index) {
        PromptInput input = new PromptInput(Painter.paintf("Enter quantity", Painter.Color.YELLOW));
        try {
            int amount = Integer.parseInt(input.nextLine());

            if (amount <= 0) {
                view.setStatus(String.format(Painter.paint("Please choose a quantity bigger than 0.", Painter.Color.RED)));
                return;
            }

            Crop crop = storeItem.get(index - 1);

            if (storeDAO.purchaseCrop(me.getUsername(), crop.getName(), amount)) {
                view.setStatus(String.format(
                    Painter.paint("%s of seeds purchased for %d gold.", Painter.Color.GREEN),
                    TextUtils.countedWord(amount, "bag", "bags"), amount * crop.getCost()
                ));
            } else {
                view.setStatus(String.format(Painter.paint("Insufficient funds to purchase crop.", Painter.Color.RED)));
            }
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Please input a valid amount to purchase.", Painter.Color.RED));
        }
    }
}
