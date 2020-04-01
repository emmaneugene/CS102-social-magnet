package com.g1t11.socialmagnet.controller.socialmagnet;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.data.ThreadLoadDAO;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.socialmagnet.NewsFeedPageView;

public class NewsFeedController extends SocialMagnetController {
    private ThreadLoadDAO threadLoadDAO = new ThreadLoadDAO(database());

    private List<Thread> threads = new ArrayList<>();

    public NewsFeedController(Navigator nav, User me) {
        super(nav, me);
        setView(new NewsFeedPageView());
    }

    @Override
    public NewsFeedPageView getView() {
        return (NewsFeedPageView) super.getView();
    }

    @Override
    public void updateView() {
        threads = threadLoadDAO.getNewsFeedThreads(me.getUsername(), 5);
        getView().setThreads(threads);
    }

    @Override
    public void handleInput() {
        getView().showMainPrompt();

        String choice = input.nextLine();
        if (choice.length() == 0) {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        } else if (choice.equals("M")) {
            nav.pop();
        } else if (choice.charAt(0) == 'T') {
            handleThread(choice);
        } else {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        }
    }

    private void handleThread(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));

            if (index <= 0 || index > threads.size()) {
                setStatus(Painter.paint("Index out of range.", Color.RED));
                return;
            }

            nav.push(new ThreadController(nav, me, index,
                    threads.get(index - 1)));
        } catch (NumberFormatException e) {
            setStatus(Painter.paint(
                    "Use T<id> to select a thread.", Color.RED));
        }
    }
}
