package com.g1t11.socialmagnet.controller;

import java.sql.Connection;
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
import com.g1t11.socialmagnet.view.WallView;

public class WallController extends Controller {
    private FarmerDAO farmerDAO = new FarmerDAO(conn);
    private ThreadDAO threadDAO = new ThreadDAO(conn);
    
    private Farmer me;
    private List<Thread> wallThreads;

    public WallController(Connection conn) {
        super(conn);
        view = new WallView();
    }

    @Override
    public void updateView() {
        me = farmerDAO.getFarmer(nav.session().currentUser());
        wallThreads = threadDAO.getWallThreads(me, 5);
        ((WallView) view).setFarmer(me);
        ((WallView) view).setThreads(wallThreads);
        view.render();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf("[[{M}]]ain | [[{T}]]hread | [[{A}]]ccept Gift | [[{P}]]ost", Painter.Color.YELLOW));
        String choice = input.nextLine();
        if (choice.equals("M")) {
            nav.pop();
        } else if (wallThreads.size() > 0 && choice.matches(String.format("T[1-%d]", wallThreads.size()))) {
            int index = Character.getNumericValue(choice.charAt(1));
            nav.push(new ThreadController(conn, index, wallThreads.get(index - 1)));
        } else if (choice.equals("A")) {
            view.setStatus("Not ready yet...");
        } else if (choice.equals("P")) {
            handlePost();
        } else if (choice.matches("T.*")) {
        }
    }

    private void handlePost() {
        updateView();
        PromptInput input = new PromptInput("Enter your post");
        String threadContent = input.nextLine();
        List<String> tags = getRawUserTags(threadContent);
    }

    private List<String> getRawUserTags(String content) {
        Pattern p = Pattern.compile("@([A-Za-z0-9]+)");
        Matcher m = p.matcher(content);

        List<String> tags = new ArrayList<>();
        while (m.find()) { 
            tags.add(m.group(0));
        }
        return tags;
    }
}