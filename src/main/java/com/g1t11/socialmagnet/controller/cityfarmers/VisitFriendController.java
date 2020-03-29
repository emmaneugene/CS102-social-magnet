package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.data.UserDAO;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.cityfarmers.VisitFriendPageView;

public class VisitFriendController extends CityFarmersController {
    UserDAO userDAO = new UserDAO(database());

    List<User> friends;

    public VisitFriendController(Navigation nav, Farmer me) {
        super(nav, me);
        friends = userDAO.getFriends(me);
        view = new VisitFriendPageView(friends);
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf(
            "[[{M}]]ain | City [[{F}]]armers | Select choice",
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
            handleVisit(choice);
        } else {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }

    private void handleVisit(String choice) {
        try {
            int index = Integer.parseInt(choice);
            
            if (index <= 0 || index > friends.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
            } else {
                nav.push(new StealingController(nav, me, friends.get(index - 1)));
            }
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Use <id> to select a friend to visit.", Painter.Color.RED));
        }
    }
}
