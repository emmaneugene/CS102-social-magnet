package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.data.UserDAO;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.cityfarmers.VisitFriendPageView;

public class VisitFriendController extends CityFarmersController {
    UserDAO userDAO = new UserDAO(database());

    List<User> friends;

    public VisitFriendController(Navigator nav, Farmer me) {
        super(nav, me);
        friends = userDAO.getFriends(me.getUsername());
        setView(new VisitFriendPageView(friends));
    }

    @Override
    public void handleInput() {
        input.setPrompt(Painter.paintf(
                "[[{M}]]ain | City [[{F}]]armers | Select choice",
                Color.YELLOW));

        String choice = input.nextLine();
        if (choice.length() == 0) {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        } else if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
        } else if (choice.equals("F")) {
            nav.popTo(CityFarmersMainMenuController.class);
        } else if (choice.matches("-?\\d+")) {
            handleVisit(choice);
        } else {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        }
    }

    private void handleVisit(String choice) {
        try {
            int index = Integer.parseInt(choice);

            if (index <= 0 || index > friends.size()) {
                setStatus(Painter.paint("Index out of range.", Color.RED));
                return;
            }
            nav.push(new StealingController(nav, me, friends.get(index - 1)));
        } catch (NumberFormatException e) {
            setStatus(Painter.paint(
                    "Use <id> to select a friend to visit.", Color.RED));
        }
    }
}
