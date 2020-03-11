package com.g1t11.socialmagnet.view;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.kit.*;

public class LikeBarView implements View {
    int likes;
    
    int dislikes;

    public LikeBarView(int likes, int dislikes) {
        this.likes = likes;
        this.dislikes = dislikes;
    }

    @Override
    public void render() {
        String likesString = countedWord(likes, "like", "likes");
        String dislikesString = countedWord(dislikes, "dislike", "dislikes");
        String paintedTemplate = Painter.paintf("[ %{%s%}, %{%s%} ]\n",
            likes > 0 ? Color.GREEN : Color.RESET,
            dislikes > 0 ? Color.PURPLE : Color.RESET);
        System.out.printf(paintedTemplate, likesString, dislikesString);
    }

    private String countedWord(int count, String singular, String plural) {
        if (count == 1) return String.format("%d %s", count, singular);
        return String.format("%d %s", count, plural);
    }
}