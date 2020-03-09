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
 * - yield : int
 * - percentStolen : int
 * + checkProgress(): int
 * + isWilted() : boolean
 */
public class Plot {
    private Crop crop = null;

    private Date timePlanted = null;

    private boolean readyToHarvest = false;

    private boolean wilted = false;

    private int yield = 0;

    private int percentStolen = 0;

    private List<String> robberNames = new ArrayList<>();

    /** 
     * Constructor for an empty plot
    */
    public Plot() {}

    /**
     * Constructor for plot that was just planted
     */
    public Plot(Crop crop) {
        this.crop = crop;
        timePlanted = new Date();
    }

    /**
     * Constructor for a plot that was planted before
     */
    public Plot(Crop crop, Date timePlanted) {
        this.crop = crop;
        this.timePlanted = timePlanted;
    }

    public Crop getCrop() {
        return crop;
    }

    public Date getTimePlanted() {
        return timePlanted;
    }

    public boolean hasHarvest() {
        return readyToHarvest;
    }

    public boolean isWilted() {
        return wilted;
    }

    public int getYield() {
        return yield;
    }

    public int getPercentStolen() {
        return percentStolen;
    }

    public List<String> getRobbers() {
        return robberNames;
    }

    public void plantCrop(Crop crop) {
        this.crop = crop;
        this.timePlanted = new Date();
    }

    /**
     * If crop is wilted, changes readyToHarvest to 'false' and wilted to 'true'
     * If crop is fully grown, invokes updateHarvest()
     */
    public void updateStatus() {
        Date now = new Date();
        long minutesElapsed = (now.getTime() - timePlanted.getTime()) / (1000 * 60);
        if (minutesElapsed >= 2 * crop.getTime()) {
            readyToHarvest = false;
            wilted = true;
        } else if (minutesElapsed >= crop.getTime()) {
            updateYield();
        }
    }

    public void updateYield() {
        Random rand = new Random();
        yield = crop.getMinYield() + rand.nextInt(crop.getMaxYield() - crop.getMinYield());
    }
    
    public void getRobbedBy(Farmer farmer) {
        //TODO:generate a percentage of harvest (1-5) to give the robber, but keep within the limit of 20%
        robberNames.add(farmer.getUsername());
    }

    public void clear() {
        crop = null;
        timePlanted = null;
        readyToHarvest = false;
        wilted = false;
        yield = 0;
        percentStolen = 0;
        robberNames.clear();
    }
}
