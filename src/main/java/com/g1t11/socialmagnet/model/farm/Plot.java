package com.g1t11.socialmagnet.model.farm;

import java.util.Date;
import java.util.Objects;

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

    public Plot(Crop crop, Date timePlanted) {
        this.crop = crop;
        this.timePlanted = timePlanted;
    }

    public Plot(Crop crop) {
        this(crop, new Date());
    }

    /**
     * An empty plot.
     */
    public Plot() {
        this(null, null);
    }

    public Crop getCrop() {
        return crop;
    }

    public Date getTimePlanted() {
        return timePlanted;
    }

    public boolean readyToHarvest() {
        Date now = new Date();
        int minutesElapsed = (int) (now.getTime() - timePlanted.getTime()) / (1000 * 60);
        if (minutesElapsed >= 2 * crop.getMinutesToHarvest() || minutesElapsed < crop.getMinutesToHarvest()) {
            return false;
        }
        return true;
    }

    public boolean isWilted() {
        Date now = new Date();
        int minutesElapsed = (int) (now.getTime() - timePlanted.getTime()) / (1000 * 60);
        if (minutesElapsed >= 2 * crop.getMinutesToHarvest()) {
            return true;
        }
        return false;
    }

    public int getPercentProgress() {
        Date now = new Date();
        int minutesElapsed = (int) (now.getTime() - timePlanted.getTime()) / (1000 * 60);
        return Math.min(minutesElapsed * 100 / crop.getMinutesToHarvest(), 100);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Plot)) return false;
        Plot other = (Plot) o;
        return Objects.equals(crop, other.crop);
    }
}