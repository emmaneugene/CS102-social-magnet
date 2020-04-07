package com.g1t11.socialmagnet.view.page.socialmagnet;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.component.CommentComponent;
import com.g1t11.socialmagnet.view.page.PageView;

/**
 * This is a page view for Individual thread.
 */
public class ThreadPageView extends PageView {
    private int threadIndex;

    private Thread thread;

    private List<CommentComponent> commentComps = new ArrayList<>();

    /**
     * Create a Thread page view with specified thread.
     * @param threadIndex The index for thread
     * @param thread The thread that user is viewing.
     */
    public ThreadPageView(int threadIndex, Thread thread) {
        super("View a Thread");
        this.threadIndex = threadIndex;
    }

    /**
     * Sets the thread for view.
     * @param thread The thread for view.
     */
    public void setThread(Thread thread) {
        this.thread = thread;
        setComments();
    }

    /**
     * Sets the comments for the thread user is viewing.
     */
    private void setComments() {
        commentComps.clear();
        for (int i = 0; i < thread.getComments().size(); i++) {
            commentComps.add(new CommentComponent(threadIndex,
                    i + 1, thread.getComments().get(i)));
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

    /**
     * A method to render out the contents of the thread.
     */
    private void renderContent() {
        String paintedTemplate = Painter.paintf(
                "[{%d}] [{%s}]: %s\n", Color.YELLOW, Color.BLUE);
        System.out.printf(paintedTemplate, threadIndex,
                thread.getFromUsername(), thread.getContent());
    }

    /**
     * A method to render out the comments of the thread.
     */
    private void renderComments() {
        for (CommentComponent commentView : commentComps) {
            commentView.render();
        }
    }

    /**
     * A method to render out the likes for the thread.
     */
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

    /**
     * A method to render out the dislikes for the thread.
     */
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

    /**
     * A method to prompt user with available inputs (Go back to
     * main menu, Reply to thread, Like or Dislike thread).
     */
    public void showMainPromptNoKill() {
        showPrompt(Painter.paintf(
                "[[{M}]]ain | [[{R}]]eply"
                        + " | [[{L}]]ike | [[{D}]]islike",
                Color.YELLOW));
        setInputColor(Color.YELLOW);
    }

    /**
     * A method to prompt user for reply of thread.
     */
    public void showReplyPrompt() {
        showPrompt("Enter your reply");
    }
}
