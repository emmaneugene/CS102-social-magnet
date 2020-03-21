package com.g1t11.socialmagnet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.g1t11.socialmagnet.data.FarmerDAO;
import com.g1t11.socialmagnet.data.ThreadDAO;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.WallPageView;

public class WallController extends Controller {
    protected FarmerDAO farmerDAO = new FarmerDAO(database());
    protected ThreadDAO threadDAO = new ThreadDAO(database());
    
    protected Farmer farmerToDisplay;
    protected List<Thread> wallThreads;

    public WallController(Navigation nav) {
        super(nav);
        farmerToDisplay = farmerDAO.getFarmer(nav.session().currentUser());
        view = new WallPageView(farmerToDisplay);
    }

    @Override
    public void updateView() {
        wallThreads = threadDAO.getWallThreads(farmerToDisplay, 5);
        ((WallPageView) view).setThreads(wallThreads);
        view.display();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf("[[{M}]]ain | [[{T}]]hread | [[{A}]]ccept Gift | [[{P}]]ost", Painter.Color.YELLOW));
        String choice = input.nextLine();
        if (choice.length() == 0) {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        } else if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
        } else if (choice.charAt(0) == 'T') {
            handleThread(choice);
        } else if (choice.equals("A")) {
            view.setStatus("Not ready yet...");
        } else if (choice.equals("P")) {
            handlePost();
        } else {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }

    protected void handleThread(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));
            if (index <= 0 || index > wallThreads.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
            } else {
                nav.push(new ThreadController(nav, index, wallThreads.get(index - 1)));
            }
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Use T<id> to select a thread.", Painter.Color.RED));
        }
    }

    protected void handlePost() {
        updateView();
        PromptInput input = new PromptInput("Enter your post");
        String threadContent = input.nextLine();
        List<String> tags = getRawUserTags(threadContent);
        threadDAO.addThread(
            nav.session().currentUser().getUsername(),
            farmerToDisplay.getUsername(),
            threadContent,
            tags
        );
    }

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