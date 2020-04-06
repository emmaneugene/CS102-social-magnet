package com.g1t11.socialmagnet.controller.socialmagnet;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.data.FarmerActionDAO;
import com.g1t11.socialmagnet.data.FarmerLoadDAO;
import com.g1t11.socialmagnet.data.ThreadActionDAO;
import com.g1t11.socialmagnet.data.ThreadLoadDAO;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.socialmagnet.WallPageView;

/**
 * This is a controller for Wall page.
 */
public class WallController extends SocialMagnetController {
    protected FarmerLoadDAO farmerLoadDAO = new FarmerLoadDAO(database());
    protected FarmerActionDAO farmerActionDAO = new FarmerActionDAO(database());
    protected ThreadLoadDAO threadLoadDAO = new ThreadLoadDAO(database());
    protected ThreadActionDAO threadActionDAO = new ThreadActionDAO(database());

    protected Farmer farmerToDisplay;
    protected List<Thread> wallThreads;

    /**
     * Creates a controller for Wall page.
     * @param nav The app's navigator.
     * @param me The user.
     */
    public WallController(Navigator nav, User me) {
        super(nav, me);
        farmerToDisplay = farmerLoadDAO.getFarmer(me.getUsername());
        setView(new WallPageView(farmerToDisplay));
    }

    @Override
    public WallPageView getView() {
        return (WallPageView) super.getView();
    }

    @Override
    public void updateView() {
        wallThreads = threadLoadDAO.getWallThreads(
                farmerToDisplay.getUsername(), 5);
        getView().setThreads(wallThreads);
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
        } else if (choice.charAt(0) == 'T') {
            handleThread(choice);
        } else if (choice.equals("A")) {
            handleAccept();
        } else if (choice.equals("P")) {
            handlePost();
        } else {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        }
    }

    /**
     * A method to handle viewing of thread. It will check for index out of 
     * range.
     * @param choice The input choice of viewing of thread.
     */
    protected void handleThread(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));

            if (index <= 0 || index > wallThreads.size()) {
                setStatus(Painter.paint("Index out of range.", Color.RED));
                return;
            }
            nav.push(new ThreadController(nav, me, index,
                    wallThreads.get(index - 1)));
        } catch (NumberFormatException e) {
            setStatus(Painter.paint(
                    "Use T<id> to select a thread.", Color.RED));
        }
    }

    /**
     * A method to handle accepting of gifts.
     */
    private void handleAccept() {
        farmerActionDAO.acceptGifts(me.getUsername());

        setStatus(Painter.paint("Accepted all pending gifts!", Color.GREEN));
    }

    /**
     * A method to handle posting of thread.
     */
    protected void handlePost() {
        // Clear the previous prompt by refreshing the view.
        getView().display();
        getView().showPostPrompt();

        String threadContent = input.nextLine();
        List<String> tags = getRawUserTags(threadContent);

        threadActionDAO.addThread(me.getUsername(),
                farmerToDisplay.getUsername(), threadContent, tags);
    }

    /**
     * Gets the tags of user in the content of thread.
     * @param content The content of thread.
     * @return The list of user that is tagged.
     */
    private List<String> getRawUserTags(String content) {
        Pattern p = Pattern.compile("@([A-Za-z0-9]+)");
        Matcher m = p.matcher(content);

        List<String> tags = new ArrayList<>();
        while (m.find()) {
            tags.add(m.group(1));
        }
        return tags;
    }
}
