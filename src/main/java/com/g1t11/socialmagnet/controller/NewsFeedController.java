package com.g1t11.socialmagnet.controller;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.data.ThreadDAO;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.NewsFeedPageView;

public class NewsFeedController extends Controller {
    private ThreadDAO threadDAO = new ThreadDAO(connection());

    private List<Thread> threads = new ArrayList<>();

    public NewsFeedController(Navigation nav) {
        super(nav);
        view = new NewsFeedPageView();
    }

    @Override
    public void updateView() {
        threads = threadDAO.getNewsFeedThreads(nav.session().currentUser(), 5);
        ((NewsFeedPageView) view).setThreads(threads);
        view.display();
    }
    
    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf("[[{M}]]ain | [[{T}]]hread", Painter.Color.YELLOW));
        String choice = input.nextLine();
        if (choice.equals("M")) {
            nav.pop();
        } else if (threads.size() > 0 && choice.matches(String.format("T[1-%d]", threads.size()))) {
            int index = Character.getNumericValue(choice.charAt(1));
            nav.push(new ThreadController(nav, index, threads.get(index - 1)));
        } else if (choice.matches("T.*")) {
            view.setStatus(Painter.paint("Use T<id> to select a thread.", Painter.Color.RED));
        } else {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }
}