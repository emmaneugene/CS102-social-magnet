package com.g1t11.socialmagnet.view;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Post;
import com.g1t11.socialmagnet.view.kit.*;

public class NewsFeedView extends PageView {
    List<PostView> postViews = new ArrayList<>(5);

    public NewsFeedView() {
        super("News Feed");
    }

    @Override
    public void render() {
        super.render();
        int index = 1;
        for (PostView postView : postViews) {
            System.out.print(index++ + " ");
            postView.render();
        }
    }

    public void setPosts(List<Post> posts) {
        postViews.clear();
        for (Post post : posts) {
            postViews.add(new PostView(post));
        }
    }
}