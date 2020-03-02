package com.g1t11.socialmagnet.model;

import java.util.Date;

/**
 * Gift
 * - sender : Farmer/User(?)
 * - recipient : Farmer/User(?)
 * - crop : Crop
 * - timestamp : Date
 */
public class Gift {
    private Farmer sender;
    private Farmer recipient;
    private Crop crop;
    private Date timestamp;
}