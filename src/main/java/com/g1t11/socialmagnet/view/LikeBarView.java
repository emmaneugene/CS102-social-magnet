package com.g1t11.socialmagnet.view;

import com.g1t11.socialmagnet.view.kit.*;
import com.g1t11.socialmagnet.view.kit.TextView.Color;

public class LikeBarView implements View {
    private TextView likeView;

    private TextView dislikeView;

    public LikeBarView(int likes, int dislikes) {
        this.likeView = new TextView(countedWord(likes, "like", "likes"), Color.YELLOW);
        this.dislikeView = new TextView(countedWord(likes, "dislike", "dislikes"), Color.PURPLE);
    }

    @Override
    public void render() {
        System.out.printf("[ %s, %s ]\n", likeView, dislikeView);
    }

    private String countedWord(int count, String singular, String plural) {
        if (count == 1) return String.format("%d %s", count, singular);
        return String.format("%d %s", count, plural);
    }
}