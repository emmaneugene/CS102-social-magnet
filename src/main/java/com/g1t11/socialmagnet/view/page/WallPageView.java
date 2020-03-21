package com.g1t11.socialmagnet.view.page;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.view.component.ProfileInfoComponent;
import com.g1t11.socialmagnet.view.component.SimpleThreadComponent;

public class WallPageView extends PageView {
    private ProfileInfoComponent profileComp;
    private List<SimpleThreadComponent> threadComps = new ArrayList<>(5);

    public WallPageView(Farmer me) {
        super("My Wall");
        this.profileComp = new ProfileInfoComponent(me);
    }

    protected WallPageView(Farmer me, String title) {
        super(title);
        this.profileComp = new ProfileInfoComponent(me);
    }

    @Override
    public void display() {
        super.display();
        profileComp.render();
        for (SimpleThreadComponent threadComp : threadComps) {
            threadComp.render();
            System.out.println();
        }
    }

    public void setThreads(List<Thread> threads) {
        threadComps.clear();
        int index = 1;
        for (Thread thread : threads) {
            threadComps.add(new SimpleThreadComponent(index++, thread));
        }
    }
}