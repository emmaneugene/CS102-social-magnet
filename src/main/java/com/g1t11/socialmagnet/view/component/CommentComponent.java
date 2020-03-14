package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.model.social.Comment;
import com.g1t11.socialmagnet.util.Painter;

public class CommentComponent implements Component {
    private final static String template = Painter.paintf("  %d.%d [{%s}]: %s\n", Painter.Color.BLUE);

    private final int postIndex;

    private final int index;

    private final Comment comment;

    public CommentComponent(int postIndex, int index, Comment comment) {
        this.postIndex = postIndex;
        this.index = index;
        this.comment = comment;
    }

    @Override
    public void render() {
        System.out.printf(template, postIndex, index, comment.getUsername(), comment.getContent());
    }
}