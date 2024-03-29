package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.cityfarmers.FarmlandPageView;

/**
 * This is a controller for My Farmland.
 */
public class FarmlandController extends CityFarmersController {
    private static final int WILTED_CLEAR_COST = 5;

    private List<Plot> plots = new ArrayList<>();
    private Map<String, Integer> invCrops = new LinkedHashMap<>();

    /**
     * Creates a Farmland controller.
     * @param nav The app's navigator.
     * @param me The farmer.
     */
    public FarmlandController(Navigator nav, Farmer me) {
        super(nav, me);
        setView(new FarmlandPageView());
    }

    @Override
    public FarmlandPageView getView() {
        return (FarmlandPageView) super.getView();
    }

    @Override
    public void updateView() {
        super.updateView();

        plots = farmerLoadDAO.getPlots(
                me.getUsername(), me.getMaxPlotCount());
        invCrops = farmerLoadDAO.getInventoryCrops(me.getUsername());
        List<String> invCropNames = List.of(
                invCrops.keySet().toArray(new String[0]));

        getView().setPlots(plots);
        getView().setInventoryCropNames(invCropNames);
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
        } else if (choice.charAt(0) == 'P') {
            handlePlant(choice);
        } else if (choice.charAt(0) == 'C') {
            handleClear(choice);
        } else if (choice.equals("H")) {
            handleHarvest();
        } else {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        }
    }

    /**
     * A method to handle planting of crops at specific plot. It will check
     * for out of range choice and if the specific plot is not empty. It will
     * not plant the crop if it fits any of the previously stated conditions.
     * @param choice The choice to plot the crop at.
     */
    private void handlePlant(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));

            if (index <= 0 || index > plots.size()) {
                setStatus(Painter.paint("Index out of range.", Color.RED));
                return;
            }

            if (!isSelectedPlotEmpty(index)) {
                return;
            }

            String cropToPlantName = getCropToPlantName();
            if (cropToPlantName == null) {
                return;
            }
            farmerActionDAO.plantCrop(me.getUsername(), index, cropToPlantName);

            setStatus(Painter.paint(
                    String.format("Planted %s in plot %d!",
                            cropToPlantName, index),
                    Color.GREEN));
        } catch (NumberFormatException e) {
            setStatus(Painter.paint("Use P<id> to select a plot.", Color.RED));
        }

    }

    /**
     * Check if the selected plot has crops growing on it.
     * @param index The index of the selected plot.
     * @return Whether the selected plot is empty.
     */
    private boolean isSelectedPlotEmpty(int index) {
        Plot selectedPlot = plots.get(index - 1);
        if (selectedPlot.getCrop() != null) {
            setStatus(Painter.paint(
                    "Cannot plant on an existing crop.", Color.RED));
            return false;
        }
        return true;
    }

    /**
     * Handle user input to get the name of the selected crop from the user's
     * inventory to plant, or handle navigation if "M" or "F" is entered.
     * @return The name of the crop to plant, or null if the choice is invalid.
     */
    private String getCropToPlantName() {
        getView().displayInvMenu();
        getView().showGetCropPrompt();

        String choice = input.nextLine();
        if (choice.length() == 0) {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
            return null;
        }
        if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
            return null;
        }
        if (choice.equals("F")) {
            nav.popTo(CityFarmersMainMenuController.class);
            return null;
        }
        return getCropToPlantFromInput(choice);
    }

    /**
     * Get the selected crop from the user inventory given user input.
     * @param choice The selection index.
     * @return The name of the selected crop from the inventory, or null if the
     * choice is invalid.
     */
    private String getCropToPlantFromInput(String choice) {
        try {
            int index = Integer.parseInt(choice);

            if (index <= 0 || index > invCrops.size()) {
                setStatus(Painter.paint("Index out of range.", Color.RED));
                return null;
            }

            String[] invCropNames = invCrops.keySet().toArray(new String[0]);
            return invCropNames[index - 1];
        } catch (NumberFormatException e) {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
            return null;
        }
    }

    /**
     * A method to handle clearing of plot. It will check for out of range
     * choice and if the selected plot is empty. It will not clear the plot if
     * it fits any of the previously stated conditions.
     * @param choice The choice of plot to clear.
     */
    private void handleClear(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));

            if (index <= 0 || index > plots.size()) {
                setStatus(Painter.paint("Index out of range.", Color.RED));
                return;
            }

            if (plots.get(index - 1).getCrop() == null) {
                setStatus(Painter.paint("Cannot clear empty plot.", Color.RED));
                return;
            }

            Plot toClear = plots.get(index - 1);
            if (!toClear.isWilted()) {
                confirmClearHealthy(index);
                return;
            }

            if (me.getWealth() < WILTED_CLEAR_COST) {
                setStatus(Painter.paint(
                        "Insufficient funds to clear wilted crop.", Color.RED));
                return;
            }

            farmerActionDAO.clearPlot(me.getUsername(), index);

            setStatus(Painter.paint(
                    String.format("Cleared plot %d for %d gold!",
                            index, WILTED_CLEAR_COST),
                    Color.GREEN));
        } catch (NumberFormatException e) {
            setStatus(Painter.paint("Use C<id> to select a plot.", Color.RED));
        }
    }

    /**
     * A method to ask user for comfirmation of clearing of healthy crop.
     * @param index The index of the plot to clear.
     */
    private void confirmClearHealthy(int index) {
        getView().showConfirmClearPrompt();

        if (input.nextLine().equals("Y")) {
            farmerActionDAO.clearPlot(me.getUsername(), index);

            setStatus(Painter.paint(
                    String.format("Cleared plot %d!", index),
                    Color.GREEN));
        }
    }

    /**
     * A method to handle harvesting of crop in the plot.
     */
    private void handleHarvest() {
        farmerActionDAO.harvest(me.getUsername());
        boolean harvested = plots.stream()
                .anyMatch(plot -> plot.readyToHarvest());
        if (harvested) {
            setStatus(Painter.paint("Harvested crops!", Color.GREEN));
        } else {
            setStatus(Painter.paint("No crops to harvest.", Color.RED));
        }
    }
}
