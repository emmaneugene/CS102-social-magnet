package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.g1t11.socialmagnet.controller.Controller;
import com.g1t11.socialmagnet.controller.MainMenuController;
import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.data.FarmerDAO;
import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.cityfarmers.FarmlandPageView;

public class FarmlandController extends Controller {
    FarmerDAO farmerDAO = new FarmerDAO(nav.database());

    Farmer me;

    List<Plot> plots = new ArrayList<>();

    public FarmlandController(Navigation nav, Farmer me) {
        super(nav);
        this.me = me;
        view = new FarmlandPageView(me);
    }

    @Override
    public void updateView() {
        plots = farmerDAO.getPlots(me);
        ((FarmlandPageView) view).setPlots(plots);
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
            Crop toPlant = handleSelectCrop();
            if (toPlant == null) return;
            System.out.println(toPlant.getName());
            farmerDAO.plantCrop(me, index, toPlant);
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Use P<id> to select a plot.", Painter.Color.RED));
        }
    }

    private Crop handleSelectCrop() {
        ((FarmlandPageView) view).displayInvMenu();
        PromptInput input = new PromptInput(Painter.paintf(
            "[[{M}]]ain | City [[{F}]]armers",
            Painter.Color.YELLOW
        ));
        String choice = input.nextLine();
        try {
            int index = Integer.parseInt(choice);
            List<Crop> crops = me.getInventory().availableCrops();
            if (index <= 0 || index > crops.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
                return null;
            }
            return crops.get(index - 1);
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
        return null;
    }
}