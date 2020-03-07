package com.g1t11.socialmagnet.controller;

import java.sql.Connection;

import com.g1t11.socialmagnet.data.PostDAO;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.NewsFeedView;

public class NewsFeedController extends Controller {
    PostDAO postDAO = new PostDAO(conn);

    public NewsFeedController(Connection conn) {
        super(conn);
        view = new NewsFeedView();
    }

    @Override
    public void updateView() {
        String currentUsername = nav.getSession().currentUser().getUsername();
        ((NewsFeedView) view).setPosts(postDAO.getNewsFeedPosts(currentUsername, 5));
        view.render();
    }
    
    @Override
    public void handleInput() {
        PromptInput input = new PromptInput("");
        switch (input.nextLine()) {
            
        }
    }
}