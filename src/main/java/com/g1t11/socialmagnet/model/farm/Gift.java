package com.g1t11.socialmagnet.model.farm;

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

    public Gift(Farmer sender, Farmer recipient, Crop crop) {
        this.sender = sender;
        this.recipient = recipient;
        this.crop = crop;
        timestamp = new Date();
    }

    public Farmer getSender() {
        return sender;
    }

    public Farmer getRecipient() {
        return recipient;
    }

    public Crop getCrop() {
        return crop;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
