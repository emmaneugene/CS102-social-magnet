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
        view.render();
    }
    
    @Override
    public void handleInput() {
        String promptText;
        if (isRemovable() || thread.isTagged()) {
            promptText = Painter.paintf("[[{M}]]ain | [[{K}]]ill | [[{R}]]eply | [[{L}]]ike | [[{D}]]islike",
                Painter.Color.YELLOW,
                Painter.Color.YELLOW,
                Painter.Color.YELLOW,
                Painter.Color.YELLOW,
                Painter.Color.YELLOW);
        } else {
            promptText = Painter.paintf("[[{M}]]ain | [[{R}]]eply | [[{L}]]ike | [[{D}]]islike",
                Painter.Color.YELLOW,
                Painter.Color.YELLOW,
                Painter.Color.YELLOW,
                Painter.Color.YELLOW);
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
            default:
                view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }

    private boolean isRemovable() {
        String currentUsername = nav.getSession().currentUser().getUsername();
        return thread.getFromUsername().equals(currentUsername) 
            || thread.getToUsername().equals(currentUsername);
    }

    private void handleKill() {
        if (!isRemovable() && !thread.isTagged()) {
            view.setStatus(Painter.paint("This post is not killable.", Painter.Color.RED));
            return;
        }
        if (isRemovable()) {
            threadDAO.deleteThread(thread, nav.getSession().currentUser());
        } else if (thread.isTagged()) {
            threadDAO.removeTag(thread, nav.getSession().currentUser());
        }
        nav.pop();
        nav.currentController().view.setStatus(Painter.paint("Successfully killed post!", Painter.Color.GREEN));
    }
}