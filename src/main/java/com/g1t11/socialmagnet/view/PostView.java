package com.g1t11.socialmagnet.view;

import com.g1t11.socialmagnet.model.social.Post;
import com.g1t11.socialmagnet.view.kit.*;

public class PostView implements View {
    Post post;
    
    public PostView(Post post) {
        this.post = post;
    }

    @Override
    public void render() {
        System.out.printf("%s: %s\n", post.getFromUsername(), post.getContent());
        int likes = post.getLikes().size();
        int dislikes = post.getDislikes().size();
        System.out.printf("[ %d %s, %d %s ]\n",
            likes, likes == 1 ? "like" : "likes",
            dislikes, dislikes == 1 ? "dislike" : "dislikes"
        );
    }
}