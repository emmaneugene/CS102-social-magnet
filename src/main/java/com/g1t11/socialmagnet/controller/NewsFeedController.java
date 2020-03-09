package com.g1t11.socialmagnet.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.data.ThreadDAO;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.NewsFeedView;

public class NewsFeedController extends Controller {
    private ThreadDAO threadDAO = new ThreadDAO(conn);

    private List<Thread> threads = new ArrayList<>();

    public NewsFeedController(Connection conn) {
        super(conn);
        view = new NewsFeedView();
    }

    @Override
    public void updateView() {
        String currentUsername = nav.getSession().currentUser().getUsername();
        threads = threadDAO.getNewsFeedThreads(currentUsername, 5);
        ((NewsFeedView) view).setThreads(threads);
        view.render();
    }
    
    @Override
    public void handleInput() {
        PromptInput input = new PromptInput("[M]ain | [T]hread");
        String choice = input.nextLine();
        if (choice.equals("M")) {
            nav.pop();
        } else if (threads.size() > 0 && choice.matches(String.format("T[1-%d]", threads.size()))) {
            int index = Character.getNumericValue(choice.charAt(1));
            nav.prepareForNavigation(new ThreadController(conn, index, threads.get(index - 1)));
        } else {
            view.setStatus("Please select a valid option.");
        }
    }
}