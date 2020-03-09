package com.g1t11.socialmagnet.model.social;

import java.util.ArrayList;
import java.util.Date;

/**
 * Post
 * - author : User
 * - recipient : User
 * - content : String
 * - likes : ArrayList<User>
 * - dislikes : ArrayList<User>
 * - tagged : ArrayList<User>
 * - comments : ArrayList<Comment>
 * - timestamp: Date
 */
public class Post {
    private User author;

    private User recipient;

    private String content;

    private ArrayList<User> likes;

    private ArrayList<User> dislikes;

    private ArrayList<User> tagged;

    private ArrayList<Comment> comments;

    private Date timestamp;
    
}
