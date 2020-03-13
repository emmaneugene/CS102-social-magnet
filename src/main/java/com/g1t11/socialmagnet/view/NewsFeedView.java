package com.g1t11.socialmagnet.view;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.view.kit.*;

public class NewsFeedView extends PageView {
    private List<SimpleThreadView> threadViews = new ArrayList<>(5);

    public NewsFeedView() {
        super("News Feed");
    }

    @Override
    public void render() {
        super.render();
        for (SimpleThreadView threadView : threadViews) {
            threadView.render();
            System.out.println();
        }
    }

    public void setThreads(List<Thread> threads) {
        threadViews.clear();
        int index = 1;
        for (Thread thread : threads) {
            threadViews.add(new SimpleThreadView(index++, thread));
        }
    }
}