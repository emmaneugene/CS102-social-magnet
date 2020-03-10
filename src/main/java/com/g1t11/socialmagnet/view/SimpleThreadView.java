package com.g1t11.socialmagnet.view;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.view.kit.*;

public class SimpleThreadView implements View {
    private final static int commentsToDisplay = 3;

    private int threadIndex;

    private Thread thread;

    private LikeBarView likeBarView;

    private List<CommentView> commentViews = new ArrayList<>(commentsToDisplay);
    
    public SimpleThreadView(int threadIndex, Thread thread) {
        this.threadIndex = threadIndex;
        this.thread = thread;
        likeBarView = new LikeBarView(thread.getLikers().size(), thread.getDislikers().size());
        setComments();
    }

    private void setComments() {
        commentViews.clear();
        int maxIndex = thread.getActualCommentsCount();
        /**
         * If we have more comments than we can display, we want to offset the
         * such that the last comment rendered has the maximum index.
         */
        int offset = maxIndex > commentsToDisplay ? maxIndex - commentsToDisplay : 0;
        for (int i = 0; i < thread.getComments().size(); i++) {
            commentViews.add(new CommentView(threadIndex, i + offset + 1, thread.getComments().get(i)));
        }
    }

    @Override
    public void render() {
        renderContent();
        likeBarView.render();
        renderComments();
    }

    private void renderContent() {
        System.out.printf("%d %s: %s\n", threadIndex, thread.getFromUsername(), thread.getContent());
    }

    private void renderComments() {
        for (CommentView commentView : commentViews) {
            commentView.render();
        }
    }
}