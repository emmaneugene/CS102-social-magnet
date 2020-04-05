package com.g1t11.socialmagnet.view.page.socialmagnet;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.component.SimpleThreadComponent;
import com.g1t11.socialmagnet.view.page.PageView;

/**
 * This is a page view for News Feed page.
 */
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

    /**
     * Sets a list of threads for this view page.
     * @param threads The list of threads.
     */
    public void setThreads(List<Thread> threads) {
        threadComps.clear();
        int index = 1;
        for (Thread thread : threads) {
            threadComps.add(new SimpleThreadComponent(index++, thread));
        }
    }

    @Override
    public void showMainPrompt() {
        showPrompt(Painter.paintf("[[{M}]]ain | [[{T}]]hread", Color.YELLOW));
        setInputColor(Color.YELLOW);
    }
}
