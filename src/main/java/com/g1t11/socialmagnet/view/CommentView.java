package com.g1t11.socialmagnet.view;

import com.g1t11.socialmagnet.model.social.Comment;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.view.kit.*;

public class CommentView implements View {
    private int postIndex;

    private int index;

    Comment comment;

    public CommentView(int postIndex, int index, Comment comment) {
        this.postIndex = postIndex;
        this.index = index;
        this.comment = comment;
    }

    @Override
    public void render() {
        String paintedTemplate = Painter.paintf("  %d.%d %{%s%}: %s\n", Painter.Color.BLUE);
        System.out.printf(paintedTemplate, postIndex, index, comment.getUsername(), comment.getContent());
    }
}