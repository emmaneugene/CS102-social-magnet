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
    private Crop crop = null;

    private Date timePlanted = null;

    private boolean readyToHarvest = false;

    private boolean wilted = false;

    private int percentProgress = 0;

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

    public int getPercentProgress() {
        return percentProgress;
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
     * Otherwise, updates percentProgress
     */
    public void updateStatus() {
        Date now = new Date();
        long minutesElapsed = (now.getTime() - timePlanted.getTime()) / (1000 * 60);
        if (minutesElapsed >= 2 * crop.getTime()) {
            readyToHarvest = false;
            wilted = true;
        } else if (minutesElapsed >= crop.getTime()) {
            updateYield();
        } else {
            percentProgress = (int) (minutesElapsed / crop.getTime() * 100);
        }
    }

    /**
     * Generates the quantity yield of the crop as a random number between minYield and maxYield
     * This function should only be called ONCE per crop growing  
     */ 
    public void updateYield() {
        Random rand = new Random();
        yield = crop.getMinYield() + rand.nextInt(crop.getMaxYield() - crop.getMinYield());
    }
    
    /**
     * Allows a farmer to harvest his own crop, and clears the plot thereafter
     * @return the quantity of crop harvested
     */
    public int harvest() {
        int harvestQuantity = yield * (100 - percentStolen) / 100;
        clear();
        return harvestQuantity;
    }

    /** 
     * Processes a robbery by a farmer
     * - adds farmer's username to robberNames
     * - updates percentStolen
     * @return the quantity of crop stolen
     */ 
    public int getRobbedBy(Farmer farmer) {
        Random rand = new Random();
        int percentStolenByFarmer = Math.min(1 + rand.nextInt(4), 20 - percentStolen);
        percentStolen += percentStolenByFarmer;
        robberNames.add(farmer.getUsername());
        return yield * percentStolenByFarmer / 100;
    }

    public void clear() {
        crop = null;
        timePlanted = null;
        readyToHarvest = false;
        wilted = false;
        percentProgress = 0;
        yield = 0;
        percentStolen = 0;
        robberNames.clear();
    }
}
