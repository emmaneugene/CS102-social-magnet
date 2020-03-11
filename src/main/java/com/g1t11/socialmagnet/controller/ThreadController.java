package com.g1t11.socialmagnet.controller;

import java.sql.Connection;

import com.g1t11.socialmagnet.data.ThreadDAO;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.ThreadView;

public class ThreadController extends Controller {
    private ThreadDAO threadDAO = new ThreadDAO(conn);

    private Thread thread;

    public ThreadController(Connection conn) {
        super(conn);
        thread = null;
        view = new ThreadView(0, null);
    }

    public ThreadController(Connection conn, int threadIndex, Thread thread) {
        super(conn);
        this.thread = thread;
        view = new ThreadView(threadIndex, thread);
    }

    @Override
    public void updateView() {
        ((ThreadView) view).setThread(thread);
        view.render();
    }
    
    @Override
    public void handleInput() {
        String promptText;
        if (isRemovable() || thread.isTagged()) {
            promptText = Painter.paintf("[[{M}]]ain | [[{K}]]ill | [[{R}]]eply | [[{L}]]ike | [[{D}]]islike",
                Painter.Color.YELLOW, Painter.Color.YELLOW, Painter.Color.YELLOW, Painter.Color.YELLOW, Painter.Color.YELLOW);
        } else {
            promptText = Painter.paintf("[[{M}]]ain | [[{R}]]eply | [[{L}]]ike | [[{D}]]islike",
                Painter.Color.YELLOW, Painter.Color.YELLOW, Painter.Color.YELLOW, Painter.Color.YELLOW);
        }
        PromptInput input = new PromptInput(promptText);
        String choice = input.nextLine();
        switch (choice) {
            case "M":
                nav.pop(2);
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
            threadDAO.deleteThread(thread, nav.session().currentUser());
            nav.pop();
            nav.currentController().view.setStatus(Painter.paint("Successfully removed post!", Painter.Color.GREEN));
        } else if (thread.isTagged()) {
            threadDAO.removeTag(thread, nav.session().currentUser());
            nav.pop();
            nav.currentController().view.setStatus(Painter.paint("Successfully untagged post!", Painter.Color.GREEN));
        }
    }

    private boolean isRemovable() {
        String currentUsername = nav.session().currentUser().getUsername();
        return thread.getFromUsername().equals(currentUsername) 
            || thread.getToUsername().equals(currentUsername);
    }

    private void handleReply() {
        // Refresh the view to remove the previous prompt
        updateView();
        PromptInput input = new PromptInput("Enter your reply");
        String reply = input.nextLine();
        threadDAO.replyToThread(thread, nav.session().currentUser(), reply);
        thread = threadDAO.getThread(thread.getId(), nav.session().currentUser());
    }

    private void handleLike() {
        threadDAO.likeThread(thread, nav.session().currentUser());
        thread = threadDAO.getThread(thread.getId(), nav.session().currentUser());
    }

    private void handleDislike() {
        threadDAO.dislikeThread(thread, nav.session().currentUser());
        thread = threadDAO.getThread(thread.getId(), nav.session().currentUser());
    }
}