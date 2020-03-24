package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.ArrayList;
import java.util.List;

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

    List<String> invCropNames = new ArrayList<>();

    public FarmlandController(Navigation nav, Farmer me) {
        super(nav);
        this.me = me;
        view = new FarmlandPageView(me);
    }

    @Override
    public void updateView() {
        plots = farmerDAO.getPlots(me);
        invCropNames = farmerDAO.getInventoryCropNames(me);
        ((FarmlandPageView) view).setPlots(plots);
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
        } else {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }

    private void handlePlant(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));
            if (index <= 0 || index > plots.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
                return;
            }

            String toPlant = handleInventory();
            if (toPlant == null) return;
            farmerDAO.plantCrop(me, index, toPlant);

        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Use P<id> to select a plot.", Painter.Color.RED));
        }
    }

    private String handleInventory() {
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
            return handleSelectCrop(choice);
        }
    }

    private String handleSelectCrop(String choice) {
        try {
            int index = Integer.parseInt(choice);
            List<String> cropNames = farmerDAO.getInventoryCropNames(me);
            if (index <= 0 || index > cropNames.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
                return null;
            }
            return cropNames.get(index - 1);
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
        return null;
    }
}