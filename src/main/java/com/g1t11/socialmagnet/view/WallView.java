package com.g1t11.socialmagnet.view;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.view.kit.*;

public class WallView extends PageView {
    private Farmer me;
    private List<SimpleThreadView> threadViews = new ArrayList<>(5);

    public WallView() {
        super("My Wall");
    }

    @Override
    public void render() {
        super.render();
        for (SimpleThreadView threadView : threadViews) {
            threadView.render();
            System.out.println();
        }
    }

    public void setFarmer(Farmer me) {
        this.me = me;
    }

    public void setThreads(List<Thread> threads) {
        threadViews.clear();
        int index = 1;
        for (Thread thread : threads) {
            threadViews.add(new SimpleThreadView(index++, thread));
        }
    }
}