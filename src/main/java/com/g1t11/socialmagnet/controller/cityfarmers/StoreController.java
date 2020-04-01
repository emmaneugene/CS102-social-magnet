package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.data.StoreDAO;
import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.TextUtils;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.cityfarmers.StorePageView;

public class StoreController extends CityFarmersController {
    StoreDAO storeDAO = new StoreDAO(nav.database());

    List<Crop> storeItem = new ArrayList<>();

    public StoreController(Navigator nav, Farmer me) {
        super(nav, me);
        setView(new StorePageView());
    }

    @Override
    public StorePageView getView() {
        return (StorePageView) super.getView();
    }

    @Override
    public void updateView() {
        super.updateView();
        storeItem = storeDAO.getStoreItems();
        getView().setCrops(storeItem);
    }

    @Override
    public void handleInput() {
        getView().showMainPrompt();

        String choice = input.nextLine();
        if (choice.length() == 0) {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        } else if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
        } else if (choice.equals("F")) {
            nav.popTo(CityFarmersMainMenuController.class);
        } else if (choice.matches("-?\\d+")) {
            handleBuyItem(choice);
        } else {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        }
    }

    private void handleBuyItem(String choice) {
        try {
            int index = Integer.parseInt(choice);

            if (index <= 0 || index > storeItem.size()) {
                setStatus(Painter.paint("Index out of range.", Color.RED));
                return;
            }
            handleBuyQuantity(index);
        } catch (NumberFormatException e) {
            setStatus(Painter.paint(
                    "Use <id> to select a crop to purchase.", Color.RED));
        }
    }

    private void handleBuyQuantity(int index) {
        getView().showQuantityPrompt();

        String choice = input.nextLine();
        try {
            int amount = Integer.parseInt(choice);

            if (amount <= 0) {
                setStatus(String.format(Painter.paint(
                        "Please choose a quantity bigger than 0.", Color.RED)));
                return;
            }

            Crop crop = storeItem.get(index - 1);

            boolean purchaseSuccessful = storeDAO.purchaseCrop(
                    me.getUsername(), crop.getName(), amount);
            if (purchaseSuccessful) {
                setStatus(Painter.paint(
                        String.format("%s of seeds purchased for %d gold.",
                                TextUtils.countedWord(amount, "bag", "bags"),
                                amount * crop.getCost()),
                        Color.GREEN));
                return;
            }
            setStatus(Painter.paint(
                    "Insufficient funds to purchase crop.", Color.RED));
        } catch (NumberFormatException e) {
            setStatus(Painter.paint(
                    "Please input a valid amount to purchase.", Color.RED));
        }
    }
}
