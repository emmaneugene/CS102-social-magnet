package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.controller.Controller;
import com.g1t11.socialmagnet.controller.MainMenuController;
import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.data.StoreDAO;
import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.cityfarmers.StorePageView;

public class StoreController extends Controller {
    StoreDAO storeDAO = new StoreDAO(nav.database());

    Farmer me;

    List<Crop> storeItem = new ArrayList<>();

    public StoreController(Navigation nav, Farmer me) {
        super(nav);
        this.me = me;
        view = new StorePageView(me);
    }

    @Override
    public void updateView() {
        // Refresh the current user's information
        me = storeDAO.getFarmer(me);
        ((StorePageView) view).setFarmer(me);
        storeItem = storeDAO.getStoreItem();
        ((StorePageView) view).setCrops(storeItem);
        view.display();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf(
            "[[{M}]]ain | City [[{F}]]armers | Select Choice ", 
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
        PromptInput input = new PromptInput(Painter.paintf("Enter quantity ", Painter.Color.YELLOW));
        try {
            int amount = Integer.parseInt(input.nextLine());

            Crop crop = storeDAO.getStoreCrop(index);

            if (storeDAO.isAbleToPurchase(me, crop, amount)) {
                storeDAO.purchaseCrop(me, crop, amount);
                storeDAO.updateInventory(me, crop, amount);
                view.setStatus(
                    String.format(Painter.paint("%s bags of seeds purchased for %d gold", Painter.Color.GREEN),
                    amount, amount * crop.getCost()));
            } else {
                view.setStatus(String.format(Painter.paint("Unable to purchase", Painter.Color.RED)));
            }
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Please input a valid amount to purchase.", Painter.Color.RED));
        }
    }
}