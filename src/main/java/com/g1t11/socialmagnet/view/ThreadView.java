package com.g1t11.socialmagnet.view;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.view.kit.*;

public class ThreadView extends PageView {
    private final static int commentsToDisplay = 3;

    private int threadIndex;

    protected Thread thread;

    private List<CommentView> commentViews = new ArrayList<>(commentsToDisplay);
    
    public ThreadView(int threadIndex, Thread thread) {
        super("View a Thread");
        this.threadIndex = threadIndex;
        this.thread = thread;
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
        renderComments();
        System.out.println();
        renderLikes();
        System.out.println();
        renderDislikes();
    }

    protected void renderContent() {
        System.out.printf("%d %s: %s\n", threadIndex, thread.getFromUsername(), thread.getContent());
    }

    private void renderLikes() {
        System.out.println("Who likes this post:");
        int index = 1;
        for (User liker : thread.getLikers()) {
            System.out.printf("  %d. %s (%s)\n", index++, liker.getFullname(), liker.getUsername());
        }
    }

    private void renderDislikes() {
        System.out.println("Who dislikes this post:");
        int index = 1;
        for (User disliker : thread.getDislikers()) {
            System.out.printf("  %d. %s (%s)\n", index++, disliker.getFullname(), disliker.getUsername());
        }
    }

    protected void renderComments() {
        for (CommentView commentView : commentViews) {
            commentView.render();
        }
    }
}