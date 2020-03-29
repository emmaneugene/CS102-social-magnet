package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.g1t11.socialmagnet.controller.Controller;
import com.g1t11.socialmagnet.controller.MainMenuController;
import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.data.FarmerDAO;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.cityfarmers.FarmlandPageView;

public class FarmlandController extends Controller {
    FarmerDAO farmerDAO = new FarmerDAO(nav.database());

    Farmer me;

    List<Plot> plots = new ArrayList<>();

    Map<String, Integer> invCrops = new LinkedHashMap<>();

    private static final int costToClearWilted = 5;

    public FarmlandController(Navigation nav, Farmer me) {
        super(nav);
        this.me = me;
        view = new FarmlandPageView(me);
    }

    @Override
    public void updateView() {
        // Refresh the current user's information
        me = farmerDAO.getFarmer(me);
        ((FarmlandPageView) view).setFarmer(me);
        plots = farmerDAO.getPlots(me);
        ((FarmlandPageView) view).setPlots(plots);
        invCrops = farmerDAO.getInventoryCrops(me);
        List<String> invCropNames = List.of(invCrops.keySet().toArray(new String[0]));
        ((FarmlandPageView) view).setInventoryCropNames(invCropNames);
        view.display();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf(
            "[[{M}]]ain | City [[{F}]]armers | [[{P}]]lant | [[{C}]]lear | [[{H}]]arvest", 
            Painter.Color.YELLOW
        ));
        String choice = input.nextLine();
        if (choice.length() == 0) {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        } else if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
        } else if (choice.equals("F")) {
            nav.popTo(CityFarmersMainMenuController.class);
        } else if (choice.charAt(0) == 'P') {
            handlePlant(choice);
        } else if (choice.charAt(0) == 'C') {
            handleClear(choice);
        } else if (choice.equals("H")) {
            handleHarvest();
        } else {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }

    private void handlePlant(String choice) {
        if (invCrops.size() == 0) {
            view.setStatus(Painter.paint("No seeds in your inventory!", Painter.Color.RED));
            return;
        }
        try {
            int index = Integer.parseInt(choice.substring(1));
            if (index <= 0 || index > plots.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
                return;
            }
            if (plots.get(index - 1).getCrop() != null) {
                view.setStatus(Painter.paint("Cannot plant on an existing crop.", Painter.Color.RED));
                return;
            }
            String toPlant = getCropNameFromInventory();
            if (toPlant == null) return;
            farmerDAO.plantCrop(me, index, toPlant);
            view.setStatus(String.format(
                Painter.paint("Planted %s in plot %d!", Painter.Color.GREEN),
                toPlant, index
            ));
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Use P<id> to select a plot.", Painter.Color.RED));
        }
    }

    private String getCropNameFromInventory() {
        ((FarmlandPageView) view).displayInvMenu();
        PromptInput input = new PromptInput(Painter.paintf(
            "[[{M}]]ain | City [[{F}]]armers",
            Painter.Color.YELLOW
        ));
        String choice = input.nextLine();
        if (choice.length() == 0) {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
            return null;
        } else if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
            return null;
        } else if (choice.equals("F")) {
            nav.popTo(CityFarmersMainMenuController.class);
            return null;
        } else {
            return selectCropName(choice);
        }
    }

    private String selectCropName(String choice) {
        try {
            int index = Integer.parseInt(choice);
            if (index <= 0 || index > invCrops.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
                return null;
            }
            return invCrops.keySet().toArray(new String[0])[index - 1];
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
        return null;
    }

    private void handleClear(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));
            if (index <= 0 || index > plots.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
                return;
            }
            if (plots.get(index - 1).getCrop() == null) {
                view.setStatus(Painter.paint("Cannot clear empty plot.", Painter.Color.RED));
                return;
            }
            Plot toClear = plots.get(index - 1);
            if (toClear.isWilted()) {
                if (me.getWealth() < costToClearWilted) {
                    view.setStatus(Painter.paint("Insufficient funds to clear wilted crop.", Painter.Color.RED));
                    return;
                }
                farmerDAO.clearPlotUpdateWealth(me, index);
                view.setStatus(String.format(
                    Painter.paint("Cleared plot %d for %d gold!", Painter.Color.GREEN),
                    index,
                    costToClearWilted
                ));
                return;
            }
            confirmClearHealthy(index);
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Use C<id> to select a plot.", Painter.Color.RED));
        }
    }

    private void confirmClearHealthy(int index) {
        PromptInput input = new PromptInput("Confirm clearing healthy crop? (Y/n)");
        if (input.nextLine().equals("Y")) {
            farmerDAO.clearPlotUpdateWealth(me, index);
            view.setStatus(String.format(
                Painter.paint("Cleared plot %d!", Painter.Color.GREEN),
                index
            ));
        }
    }

    private void handleHarvest() {
        farmerDAO.harvest(me);
    }
}