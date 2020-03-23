package com.g1t11.socialmagnet.model.farm;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Plot
 * - crop : Crop
 * - timePlanted : Date
 * - readyToHarvest : boolean
 * - wilted : boolean
 * - percentProgress : int
 * - yield : int
 * - percentStolen : int
 * - robberNames : List<String>
 */
public class Plot {
    private Crop crop;

    private Date timePlanted;

    /**
     * Random float between 0 to 1 used to determine the percentage of yield, 
     * with 0 representing min yield, and 1 representing max yield.
     */
    private double yield;

    private int percentStolen;

    public Plot(Crop crop, Date timePlanted, int yield, int percentStolen, List<String> robberNames) {
        this.crop = crop;
        this.timePlanted = timePlanted;
    }

    public Crop getCrop() {
        return crop;
    }

    public Date getTimePlanted() {
        return timePlanted;
    }

    public double getYield() {
        return yield;
    }

    public int getPercentStolen() {
        return percentStolen;
    }

    public boolean readyToHarvest() {
        Date now = new Date();
        int minutesElapsed = (int) (now.getTime() - timePlanted.getTime()) / (1000 * 60);
        if (minutesElapsed >= 2 * crop.getTimeToHarvest() || minutesElapsed < crop.getTimeToHarvest()) {
            return false;
        }
        return true;
    }

    public boolean isWilted() {
        Date now = new Date();
        int minutesElapsed = (int) (now.getTime() - timePlanted.getTime()) / (1000 * 60);
        if (minutesElapsed >= 2 * crop.getTimeToHarvest()) {
            return true;
        }
        return false;
    }

    public int getPercentProgress() {
        Date now = new Date();
        int minutesElapsed = (int) (now.getTime() - timePlanted.getTime()) / (1000 * 60);
        return minutesElapsed * 100 / crop.getTimeToHarvest();
    }
}