package com.g1t11.socialmagnet.view.page.socialmagnet;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.component.WallProfileInfoComponent;
import com.g1t11.socialmagnet.view.page.PageView;
import com.g1t11.socialmagnet.view.component.SimpleThreadComponent;

/**
 * This is a page view for User's Wall page.
 */
public class WallPageView extends PageView {
    private WallProfileInfoComponent profileComp;
    private List<SimpleThreadComponent> threadComps = new ArrayList<>(5);

    public WallPageView(Farmer me) {
        super("My Wall");
        this.profileComp = new WallProfileInfoComponent(me);
    }

    protected WallPageView(Farmer me, String title) {
        super(title);
        this.profileComp = new WallProfileInfoComponent(me);
    }

    @Override
    public void display() {
        super.display();
        profileComp.render();
        System.out.println();

        for (SimpleThreadComponent threadComp : threadComps) {
            threadComp.render();
            System.out.println();
        }
    }

    /**
     * Sets the threads for wall page viewing.
     * @param threads The list of threads for wall page.
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
        showPrompt(Painter.paintf(
                "[[{M}]]ain | [[{T}]]hread | [[{A}]]ccept Gift | [[{P}]]ost",
                Color.YELLOW));
        setInputColor(Color.YELLOW);
    }

    public void showPostPrompt() {
        showPrompt("Enter your post");
    }
}
