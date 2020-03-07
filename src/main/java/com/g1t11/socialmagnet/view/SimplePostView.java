package com.g1t11.socialmagnet.view;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Comment;
import com.g1t11.socialmagnet.model.social.Post;
import com.g1t11.socialmagnet.view.kit.*;

public class SimplePostView implements View {
    private final static int commentsToDisplay = 3;

    private int postIndex;

    private Post post;

    private List<CommentView> commentViews = new ArrayList<>(commentsToDisplay);
    
    public SimplePostView(int postIndex, Post post) {
        this.postIndex = postIndex;
        this.post = post;
        setComments();
    }

    private void setComments() {
        commentViews.clear();
        int maxIndex = post.getActualCommentsCount();
        /**
         * If we have more comments than we can display, we want to offset the
         * such that the last comment rendered has the maximum index.
         */
        int offset = maxIndex > commentsToDisplay ? maxIndex - commentsToDisplay : 0;
        for (int i = 0; i < post.getComments().size(); i++) {
            commentViews.add(new CommentView(postIndex, i + offset + 1, post.getComments().get(i)));
        }
    }

    @Override
    public void render() {
        renderContent();
        renderLikes();
        renderComments();
    }

    private void renderContent() {
        System.out.printf("%d %s: %s\n", postIndex, post.getFromUsername(), post.getContent());
    }

    private void renderLikes() {
        int likes = post.getLikes().size();
        int dislikes = post.getDislikes().size();
        System.out.printf("[ %s, %s ]\n", countedWord(likes, "like", "likes"), countedWord(dislikes, "dislike", "dislikes"));
    }

    private String countedWord(int count, String singular, String plural) {
        if (count == 1) return String.format("%d %s", count, singular);
        return String.format("%d %s", count, plural);
    }

    private void renderComments() {
        for (CommentView commentView : commentViews) {
            commentView.render();
        }
    }
}