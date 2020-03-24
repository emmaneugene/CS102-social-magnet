package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.TextUtils;
import com.g1t11.socialmagnet.util.Painter.Color;

public class LikeBarComponent implements Component {
    int likes;
    
    int dislikes;

    public LikeBarComponent(int likes, int dislikes) {
        this.likes = likes;
        this.dislikes = dislikes;
    }

    @Override
    public void render() {
        String likesString = TextUtils.countedWord(likes, "like", "likes");
        String dislikesString = TextUtils.countedWord(dislikes, "dislike", "dislikes");
        String paintedTemplate = Painter.paintf(
            "[ [{%s}], [{%s}] ]\n",
            likes > 0 ? Color.GREEN : Color.RESET,
            dislikes > 0 ? Color.PURPLE : Color.RESET);
        System.out.printf(paintedTemplate, likesString, dislikesString);
    }
}