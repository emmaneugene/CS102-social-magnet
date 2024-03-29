package com.g1t11.socialmagnet.view.component;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * A render component for a thread in a list of threads.
 * <p>
 * Most notably used in the news feed and the wall page views.
 */
public class SimpleThreadComponent implements Component {
    private final static int commentsToDisplay = 3;

    private int threadIndex;

    private Thread thread;

    private LikeBarComponent likeBarComp;

    private List<CommentComponent> commentComps
            = new ArrayList<>(commentsToDisplay);

    /**
     * Creates a single thread component with specified thread index and thread.
     * @param threadIndex The index of thread.
     * @param thread The thread.
     */
    public SimpleThreadComponent(int threadIndex, Thread thread) {
        this.threadIndex = threadIndex;
        this.thread = thread;
        likeBarComp = new LikeBarComponent(
                thread.getLikers().size(), thread.getDislikers().size());
        setComments();
    }

    /**
     * Sets comments of the single thread.
     */
    private void setComments() {
        commentComps.clear();
        int maxIndex = thread.getActualCommentsCount();
        /**
         * If we have more comments than we can display, we want to offset the
         * such that the last comment rendered has the maximum index.
         */
        int offset = maxIndex > commentsToDisplay
                ? maxIndex - commentsToDisplay
                : 0;
        for (int i = 0; i < thread.getComments().size(); i++) {
            commentComps.add(new CommentComponent(
                    threadIndex, i + offset + 1, thread.getComments().get(i)));
        }
    }

    /**
     * A method used to render out the single thread which include content,
     * like/diislike bar and comments.
     */
    @Override
    public void render() {
        renderContent();
        likeBarComp.render();
        renderComments();
    }

    /**
     * A method used to render out the content of the single thread.
     */
    private void renderContent() {
        String paintedTemplate = Painter.paintf(
                "[{%d}] [{%s}]: %s\n", Color.YELLOW, Color.BLUE);
        System.out.printf(paintedTemplate, threadIndex,
                thread.getFromUsername(), thread.getContent());
    }

    /**
     * A method used to render out the comments of the single thread.
     */
    private void renderComments() {
        for (CommentComponent commentView : commentComps) {
            commentView.render();
        }
    }
}
