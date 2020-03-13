package com.g1t11.socialmagnet.controller;

import java.sql.Connection;
import java.util.List;

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
        wallThreads = threadDAO.getNewsFeedThreads(me, 5);
        ((WallView) view).setFarmer(me);
        ((WallView) view).setThreads(wallThreads);
        view.render();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf("[[{M}]]ain | [[{T}]]hread | [[{A}]]ccept Gift | [[{P}]]ost", Painter.Color.YELLOW, Painter.Color.YELLOW, Painter.Color.YELLOW, Painter.Color.YELLOW));
        input.nextLine();
        nav.pop();
    }
}