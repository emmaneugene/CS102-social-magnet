package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.model.social.Comment;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * This is a render component for comments.
 */
public class CommentComponent implements Component {
    private final static String template = Painter.paintf(
            "  %d.%d [{%s}]: %s\n", Color.BLUE);

    private final int postIndex;

    private final int index;

    private final Comment comment;

    /**
     * Create a comment component with specified post index, comment index 
     * and comment.
     * @param postIndex The post index.
     * @param index The comment index.
     * @param comment The comment content.
     */
    public CommentComponent(int postIndex, int index, Comment comment) {
        this.postIndex = postIndex;
        this.index = index;
        this.comment = comment;
    }

    /**
     * A method to render out the comment.
     */
    @Override
    public void render() {
        System.out.printf(template, postIndex, index,
                comment.getUsername(), comment.getContent());
    }
}
