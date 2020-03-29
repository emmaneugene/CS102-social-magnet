package com.g1t11.socialmagnet.controller.socialmagnet;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.data.ThreadDAO;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.socialmagnet.NewsFeedPageView;

public class NewsFeedController extends SocialMagnetController {
    private ThreadDAO threadDAO = new ThreadDAO(database());

    private List<Thread> threads = new ArrayList<>();

    public NewsFeedController(Navigation nav, User me) {
        super(nav, me);
        view = new NewsFeedPageView();
    }

    @Override
    public void updateView() {
        threads = threadDAO.getNewsFeedThreads(me, 5);
        ((NewsFeedPageView) view).setThreads(threads);
    }
    
    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf("[[{M}]]ain | [[{T}]]hread", Painter.Color.YELLOW));
        String choice = input.nextLine();
        if (choice.length() == 0) {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        } else if (choice.equals("M")) {
            nav.pop();
        } else if (choice.charAt(0) == 'T') {
            handleThread(choice);
        } else {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }

    private void handleThread(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));
            if (index <= 0 || index > threads.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
            } else {
                nav.push(new ThreadController(nav, me, index, threads.get(index - 1)));
            }
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Use T<id> to select a thread.", Painter.Color.RED));
        }
    }
}
