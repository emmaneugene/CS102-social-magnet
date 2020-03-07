package com.g1t11.socialmagnet.view;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Post;
import com.g1t11.socialmagnet.view.kit.*;

public class NewsFeedView extends PageView {
    private List<SimplePostView> postViews = new ArrayList<>(5);

    public NewsFeedView() {
        super("News Feed");
    }

    @Override
    public void render() {
        super.render();
        for (SimplePostView postView : postViews) {
            postView.render();
            System.out.println();
        }
    }

    public void setPosts(List<Post> posts) {
        postViews.clear();
        int index = 1;
        for (Post post : posts) {
            postViews.add(new SimplePostView(index++, post));
        }
    }
}