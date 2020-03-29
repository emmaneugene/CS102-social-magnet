package com.g1t11.socialmagnet.controller.socialmagnet;

import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.data.UserDAO;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.socialmagnet.FriendsWallPageView;

public class FriendsWallController extends WallController {
    private UserDAO userDAO = new UserDAO(database());

    public FriendsWallController(Navigation nav, User me, User friend) {
        super(nav, me);
        farmerToDisplay = farmerDAO.getFarmer(friend);
        view = new FriendsWallPageView(
            me,
            farmerToDisplay, 
            userDAO.getFriendsOfFriendWithCommon(me, farmerToDisplay)
        );
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf("[[{M}]]ain | [[{T}]]hread | [[{P}]]ost", Painter.Color.YELLOW));
        String choice = input.nextLine();
        if (choice.length() == 0) {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        } else if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
        } else if (choice.charAt(0) == 'T') {
            handleThread(choice);
        } else if (choice.equals("P")) {
            handlePost();
        } else {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }
}
