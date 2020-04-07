package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.TextUtils;
import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * This is a render component for the Like/Dislike bar.
 */
public class LikeBarComponent implements Component {
    private int likes;

    private int dislikes;

    /**
     * Creates a Like/Dislike bar component with specified amount of likes and 
     * dislike.
     * @param likes The amount of likes.
     * @param dislikes The amount of dislikes.
     */
    public LikeBarComponent(int likes, int dislikes) {
        this.likes = likes;
        this.dislikes = dislikes;
    }

    /**
     * A method used to render out the component for Like/Dislike bar.
     */
    @Override
    public void render() {
        String likesString = TextUtils.countedWord(likes, "like", "likes");
        String dislikesString
                = TextUtils.countedWord(dislikes, "dislike", "dislikes");
        String paintedTemplate = Painter.paintf(
                "[ [{%s}], [{%s}] ]\n",
                likes > 0 ? Color.GREEN : Color.RESET,
                dislikes > 0 ? Color.PURPLE : Color.RESET);
        System.out.printf(paintedTemplate, likesString, dislikesString);
    }
}
