package com.g1t11.socialmagnet.controller.socialmagnet;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.data.ThreadActionDAO;
import com.g1t11.socialmagnet.data.ThreadLoadDAO;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.socialmagnet.ThreadPageView;

/**
 * This is a controller for Thread page.
 */
public class ThreadController extends SocialMagnetController {
    private ThreadLoadDAO threadLoadDAO = new ThreadLoadDAO(database());
    private ThreadActionDAO threadActionDAO = new ThreadActionDAO(database());

    private Thread thread;

    /**
     * Creates a Thread controller.
     * @param nav The app's navigator.
     * @param me The user.
     * @param threadIndex The thread index.
     * @param thread The thread.
     */
    public ThreadController(Navigator nav, User me,
            int threadIndex, Thread thread) {
        super(nav, me);
        this.thread = thread;
        setView(new ThreadPageView(threadIndex, thread));
    }

    /**
     * Creates a Thread controller with only navigator and user.
     * @param nav The app's navigator.
     * @param me The user.
     */
    public ThreadController(Navigator nav, User me) {
        this(nav, me, 0, null);
    }

    @Override
    public ThreadPageView getView() {
        return (ThreadPageView) super.getView();
    }

    @Override
    public void updateView() {
        thread = threadLoadDAO.getThread(thread.getId(), me.getUsername());
        getView().setThread(thread);
    }

    @Override
    public void handleInput() {
        if (isRemovable() || thread.isTagged()) {
            getView().showMainPrompt();
        } else {
            getView().showMainPromptNoKill();
        }

        String choice = input.nextLine();
        switch (choice) {
            case "M":
                nav.popTo(MainMenuController.class);
                break;
            case "K":
                handleKill();
                break;
            case "R":
                handleReply();
                break;
            case "L":
                handleLike();
                break;
            case "D":
                handleDislike();
                break;
            default:
                setStatus(Painter.paint(
                        "Please select a valid option.", Color.RED));
        }
    }

    /**
     * A method to handle killing of thread. It will check it if is killable. It
     * is killable when the thread is posted by the user or when the user is 
     * tagged. When posted by user, it will remove post. When tagged, it will 
     * remove tag only.
     */
    private void handleKill() {
        if (!isRemovable() && !thread.isTagged()) {
            setStatus(Painter.paint("This post is not killable.", Color.RED));
            return;
        }
        if (isRemovable()) {
            threadActionDAO.deleteThread(thread.getId(), me.getUsername());
            nav.pop();
            nav.setCurrStatus(Painter.paint(
                    "Successfully removed post!", Color.GREEN));
        } else if (thread.isTagged()) {
            threadActionDAO.removeTag(thread.getId(), me.getUsername());
            nav.pop();
            nav.setCurrStatus(Painter.paint(
                    "Successfully untagged post!", Color.GREEN));
        }
    }

    /**
     * A method to check if the thread is removable. It is removable when the
     * thread is posted by the user.
     * @return If it is removable.
     */
    private boolean isRemovable() {
        String currentUsername = me.getUsername();
        return thread.getFromUsername().equals(currentUsername)
                || thread.getToUsername().equals(currentUsername);
    }

    /**
     * A method to handle replying of thread.
     */
    private void handleReply() {
        // Clear the previous prompt by refreshing the view.
        getView().display();
        getView().showReplyPrompt();

        String reply = input.nextLine();
        threadActionDAO.replyToThread(thread.getId(), me.getUsername(), reply);
    }

    /**
     * A method to like a thread.
     */
    private void handleLike() {
        threadActionDAO.toggleLikeThread(thread.getId(), me.getUsername());
    }

    /**
     * A method to dislike a thread.
     */
    private void handleDislike() {
        threadActionDAO.toggleDislikeThread(thread.getId(), me.getUsername());
    }
}
