package com.g1t11.socialmagnet.model;

import java.util.Date;

/**
 * Comment
 */
public class Comment {
    private Post post;
    private User author;
    private String content;
    private Date timestamp;
}