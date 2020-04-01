package com.g1t11.socialmagnet.view.page.socialmagnet;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.component.CommentComponent;
import com.g1t11.socialmagnet.view.page.PageView;

public class ThreadPageView extends PageView {
    private final static int commentsToDisplay = 3;

    private int threadIndex;

    private Thread thread;

    private List<CommentComponent> commentComps
            = new ArrayList<>(commentsToDisplay);

    public ThreadPageView(int threadIndex, Thread thread) {
        super("View a Thread");
        this.threadIndex = threadIndex;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
        setComments();
    }

    private void setComments() {
        commentComps.clear();
        int maxIndex = thread.getActualCommentsCount();
        // If we have more comments than we can display, we want to offset the
        // such that the last comment rendered has the maximum index.
        int offset = maxIndex > commentsToDisplay
                ? maxIndex - commentsToDisplay
                : 0;
        for (int i = 0; i < thread.getComments().size(); i++) {
            commentComps.add(new CommentComponent(threadIndex,
                    i + offset + 1, thread.getComments().get(i)));
        }
    }

    @Override
    public void display() {
        super.display();
        renderContent();
        renderComments();
        System.out.println();
        renderLikes();
        System.out.println();
        renderDislikes();
        System.out.println();
    }

    private void renderContent() {
        String paintedTemplate = Painter.paintf(
                "[{%d}] [{%s}]: %s\n", Color.YELLOW, Color.BLUE);
        System.out.printf(paintedTemplate, threadIndex,
                thread.getFromUsername(), thread.getContent());
    }

    private void renderComments() {
        for (CommentComponent commentView : commentComps) {
            commentView.render();
        }
    }

    private void renderLikes() {
        System.out.println(Painter.paint(
                "Who likes this post:", Color.GREEN));
        int index = 1;

        String paintedTemplate = Painter.paintf(
                "  %d. %s ([{%s}])\n", Color.BLUE);
        for (User liker : thread.getLikers()) {
            System.out.printf(paintedTemplate, index++,
                    liker.getFullname(), liker.getUsername());
        }
    }

    private void renderDislikes() {
        System.out.println(Painter.paint(
                "Who dislikes this post:", Color.PURPLE));
        int index = 1;

        String paintedTemplate = Painter.paintf(
                "  %d. %s ([{%s}])\n", Color.BLUE);
        for (User disliker : thread.getDislikers()) {
            System.out.printf(paintedTemplate, index++,
                    disliker.getFullname(), disliker.getUsername());
        }
    }

    @Override
    public void showMainPrompt() {
        showPrompt(Painter.paintf(
                "[[{M}]]ain | [[{K}]]ill | [[{R}]]eply"
                        + " | [[{L}]]ike | [[{D}]]islike",
                Color.YELLOW));
        setInputColor(Color.YELLOW);
    }

    public void showMainPromptNoKill() {
        showPrompt(Painter.paintf(
                "[[{M}]]ain | [[{R}]]eply"
                        + " | [[{L}]]ike | [[{D}]]islike",
                Color.YELLOW));
        setInputColor(Color.YELLOW);
    }

    public void showReplyPrompt() {
        showPrompt("Enter your reply");
    }
}
