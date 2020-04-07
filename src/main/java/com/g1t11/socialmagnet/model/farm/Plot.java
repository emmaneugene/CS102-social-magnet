package com.g1t11.socialmagnet.model.farm;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Plot {
    private Crop crop;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="UTC")
    private Date timePlanted;

    public Plot() {}

    public Plot(Crop crop, Date timePlanted) {
        this.crop = crop;
        this.timePlanted = timePlanted;
    }

    public Plot(Crop crop) {
        this(crop, new Date());
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public Date getTimePlanted() {
        return timePlanted;
    }

    public void setTimePlanted(Date timePlanted) {
        this.timePlanted = timePlanted;
    }

    public boolean readyToHarvest() {
        Date now = new Date();
        int minutesElapsed = (int) (now.getTime() - timePlanted.getTime())
                           / (1000 * 60);
        if (minutesElapsed >= 2 * crop.getMinutesToHarvest()
                || minutesElapsed < crop.getMinutesToHarvest()) {
            return false;
        }
        return true;
    }

    public boolean isWilted() {
        Date now = new Date();
        int minutesElapsed = (int) (now.getTime() - timePlanted.getTime())
                           / (1000 * 60);
        if (minutesElapsed >= 2 * crop.getMinutesToHarvest()) {
            return true;
        }
        return false;
    }

    public int getPercentProgress() {
        Date now = new Date();
        int minutesElapsed = (int) (now.getTime() - timePlanted.getTime())
                           / (1000 * 60);
        return Math.min(minutesElapsed * 100 / crop.getMinutesToHarvest(), 100);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Plot)) return false;
        Plot other = (Plot) o;
        return Objects.equals(crop, other.crop);
    }
}