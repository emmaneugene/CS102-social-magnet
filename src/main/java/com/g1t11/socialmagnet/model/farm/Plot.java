package com.g1t11.socialmagnet.model.farm;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Represent a plot.
 */
public class Plot {
    private Crop crop;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="UTC")
    private Date timePlanted;

    /**
     * Creates a plot with both specified crop and time planted.
     * @param crop The crop planted.
     * @param timePlanted The time crop is planted.
     */
    public Plot(Crop crop, Date timePlanted) {
        this.crop = crop;
        this.timePlanted = timePlanted;
    }

    /**
     * Creates a plot with specified crop and current time as time planted.
     * @param crop The crop planted.
     */
    public Plot(Crop crop) {
        this(crop, new Date());
    }

    /**
     * Creates an empty plot.
     */
    public Plot() {}

    /**
     * Gets the crop at plot.
     * @return The crop at plot
     */
    public Crop getCrop() {
        return crop;
    }

    /**
     * Sets crop.
     * @param crop Crop.
     */
    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public Date getTimePlanted() {
        return timePlanted;
    }

    /**
     * Sets the time of crop planted at plot.
     * @param timePlanted The time of crop planted at plot.
     */
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

    /**
     * A method to check if the crop in the plot is wilted. It is wilted when
     * the crop is ready to harvest for longer than its growing time.
     * @return The boolean value of crop in the plot is wilted.
     */
    public boolean isWilted() {
        Date now = new Date();
        int minutesElapsed = (int) (now.getTime() - timePlanted.getTime())
                           / (1000 * 60);
        if (minutesElapsed >= 2 * crop.getMinutesToHarvest()) {
            return true;
        }
        return false;
    }

    /**
     * A method to get the percentage of the progress of the crop getting
     * matured.
     * @return The integer of the percentage of crop getting matured.
     */
    public int getPercentProgress() {
        Date now = new Date();
        int minutesElapsed = (int) (now.getTime() - timePlanted.getTime())
                           / (1000 * 60);
        return Math.min(minutesElapsed * 100 / crop.getMinutesToHarvest(), 100);
    }

    /**
     * Compares the specified object with this plot for equality.
     * It returns true if and only if specified object is a plot and
     * both plot have the same crop.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Plot)) return false;
        Plot other = (Plot) o;
        return Objects.equals(crop, other.crop);
    }
}