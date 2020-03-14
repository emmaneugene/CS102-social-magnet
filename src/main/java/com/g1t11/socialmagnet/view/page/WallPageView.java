package com.g1t11.socialmagnet.view.page;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.view.component.ProfileInfoComponent;
import com.g1t11.socialmagnet.view.component.SimpleThreadComponent;

public class WallPageView extends PageView {
    private ProfileInfoComponent profileView;
    private List<SimpleThreadComponent> threadComps = new ArrayList<>(5);

    public WallPageView(Farmer me) {
        super("My Wall");
        this.profileView = new ProfileInfoComponent(me);
    }

    @Override
    public void display() {
        super.display();
        profileView.render();
        for (SimpleThreadComponent threadView : threadComps) {
            threadView.render();
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