package com.g1t11.socialmagnet.view.page;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.view.component.SimpleThreadComponent;

public class NewsFeedPageView extends PageView {
    private List<SimpleThreadComponent> threadComps = new ArrayList<>(5);

    public NewsFeedPageView() {
        super("News Feed");
    }

    @Override
    public void display() {
        super.display();
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