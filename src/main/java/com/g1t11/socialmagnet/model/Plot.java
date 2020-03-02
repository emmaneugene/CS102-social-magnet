package com.g1t11.socialmagnet.model;

import java.util.Date;

/**
 * Plot
 * - crop : Crop
 * - timePlanted : Date
 * - timeGrown : int (in minutes)
 * - harvestQuantity : int
 * - percentStolen : int
 * + isWilted() : boolean
 */
public class Plot {
    private Crop crop;
    private Date timePlanted;
    private int timeGrown;
    private int harvestQuantity;
    private int percentStolen;

    public boolean isWilted() {
        return timeGrown > 2 * crop.getGrowingTime();
    }
    
}