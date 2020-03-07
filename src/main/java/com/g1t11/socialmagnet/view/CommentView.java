package com.g1t11.socialmagnet.view;

import com.g1t11.socialmagnet.model.social.Comment;
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
        System.out.printf("  %d.%d %s: %s\n", postIndex, index, comment.getUsername(), comment.getContent());
    }
}