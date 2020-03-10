package com.g1t11.socialmagnet.controller;

import java.sql.Connection;

import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.ThreadView;

public class ThreadController extends Controller {
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
        // "[M]ain | [K]ill | [R]eply | [L]ike | [D]islike >"
        if (isKillable()) {
            promptText = "[M]ain | [K]ill | [R]eply | [L]ike | [D]islike";
        } else {
            promptText = "[M]ain | [R]eply | [L]ike | [D]islike";
        }
        PromptInput input = new PromptInput(promptText);
        String choice = input.nextLine();
        switch (choice) {
            case "M":
                nav.pop(2);
                break;
            case "K":
                if (isKillable()) {
                    view.setStatus("KILLING!!");
                    nav.pop();
                    break;
                }
            default:
                view.setStatus("Please select a valid option.");
        }
    }

    private boolean isKillable() {
        String currentUsername = nav.getSession().currentUser().getUsername();
        return thread.getFromUsername().equals(currentUsername) 
            || thread.getToUsername().equals(currentUsername) 
            || thread.isTagged();
    }
}