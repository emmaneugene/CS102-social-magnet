package com.g1t11.socialmagnet.model;

import java.util.ArrayList;

/**
 * User
 * - username : String
 * - fullName : String
 * - password : String
 * - friends : ArrayList<User>
 * - wall : ArrayList<Post>
 * - requests : ArrayList<Request>
 * - requested : ArrayList<Request> 
 */
public class User {
    private String username;
    private String fullName;
    private String password;
    private ArrayList<User> friends;
    private ArrayList<Post> feed;
    private ArrayList<Gift> gifts;
    private ArrayList<Request> requests;
    private ArrayList<Request> requested;
}