package com.g1t11.socialmagnet.view;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
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
        super.render();
        renderContent();
        renderComments();
        System.out.println();
        renderLikes();
        System.out.println();
        renderDislikes();
    }

    protected void renderContent() {
        String paintedTemplate = Painter.paintf("[{%d}] [{%s}]: %s\n", Painter.Color.YELLOW, Painter.Color.BLUE);
        System.out.printf(paintedTemplate, threadIndex, thread.getFromUsername(), thread.getContent());
    }

    private void renderLikes() {
        System.out.println(Painter.paint("Who likes this post:", Painter.Color.GREEN));
        int index = 1;

        String paintedTemplate = Painter.paintf("  %d. %s ([{%s}])\n", Painter.Color.BLUE);
        for (User liker : thread.getLikers()) {
            System.out.printf(paintedTemplate, index++, liker.getFullname(), liker.getUsername());
        }
    }

    private void renderDislikes() {
        System.out.println(Painter.paint("Who dislikes this post:", Painter.Color.PURPLE));
        int index = 1;

        String paintedTemplate = Painter.paintf("  %d. %s ([{%s}])\n", Painter.Color.BLUE);
        for (User disliker : thread.getDislikers()) {
            System.out.printf(paintedTemplate, index++, disliker.getFullname(), disliker.getUsername());
        }
    }

    protected void renderComments() {
        for (CommentView commentView : commentViews) {
            commentView.render();
        }
    }
}