package com.g1t11.socialmagnet.controller.socialmagnet;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.data.ThreadActionDAO;
import com.g1t11.socialmagnet.data.ThreadLoadDAO;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.view.page.socialmagnet.ThreadPageView;

public class ThreadController extends SocialMagnetController {
    private ThreadLoadDAO threadLoadDAO = new ThreadLoadDAO(database());
    private ThreadActionDAO threadActionDAO = new ThreadActionDAO(database());

    private Thread thread;

    public ThreadController(Navigator nav, User me, int threadIndex, Thread thread) {
        super(nav, me);
        this.thread = thread;
        view = new ThreadPageView(threadIndex, thread);
    }

    public ThreadController(Navigator nav, User me) {
        this(nav, me, 0, null);
    }

    @Override
    public void updateView() {
        ((ThreadPageView) view).setThread(thread);
    }
    
    @Override
    public void handleInput() {
        String promptText;
        if (isRemovable() || thread.isTagged()) {
            promptText = Painter.paintf("[[{M}]]ain | [[{K}]]ill | [[{R}]]eply | [[{L}]]ike | [[{D}]]islike", Painter.Color.YELLOW); 
        } else {
            promptText = Painter.paintf("[[{M}]]ain | [[{R}]]eply | [[{L}]]ike | [[{D}]]islike", Painter.Color.YELLOW); 
        }
        input.setPrompt(promptText);
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
                view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }

    private void handleKill() {
        if (!isRemovable() && !thread.isTagged()) {
            view.setStatus(Painter.paint("This post is not killable.", Painter.Color.RED));
            return;
        }
        if (isRemovable()) {
            threadActionDAO.deleteThread(thread.getId(), me.getUsername());
            nav.pop();
            nav.setCurrStatus(Painter.paint("Successfully removed post!", Painter.Color.GREEN));
        } else if (thread.isTagged()) {
            threadActionDAO.removeTag(thread.getId(), me.getUsername());
            nav.pop();
            nav.setCurrStatus(Painter.paint("Successfully untagged post!", Painter.Color.GREEN));
        }
    }

    private boolean isRemovable() {
        String currentUsername = me.getUsername();
        return thread.getFromUsername().equals(currentUsername) 
            || thread.getToUsername().equals(currentUsername);
    }

    private void handleReply() {
        // Refresh the view to remove the previous prompt
        view.display();
        input.setPrompt("Enter your reply");
        String reply = input.nextLine();
        threadActionDAO.replyToThread(thread.getId(), me.getUsername(), reply);
        thread = threadLoadDAO.getThread(thread.getId(), me.getUsername());
    }

    private void handleLike() {
        threadActionDAO.toggleLikeThread(thread.getId(), me.getUsername());
        thread = threadLoadDAO.getThread(thread.getId(), me.getUsername());
    }

    private void handleDislike() {
        threadActionDAO.toggleDislikeThread(thread.getId(), me.getUsername());
        thread = threadLoadDAO.getThread(thread.getId(), me.getUsername());
    }
}
