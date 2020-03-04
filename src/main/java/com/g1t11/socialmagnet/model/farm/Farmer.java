package com.g1t11.socialmagnet.model.farm;

import java.util.ArrayList;

/**
 * Farmer extends User
 * - rank : String
 * - exp : int
 * - wealth : int
 * - farmland : ArrayList<Plot>
 * - inventory : ArrayList<InventoryItem>
 * - stolenCrops : ArrayList<Stealing>
 */
public class Farmer {
    private String rank;
    private int exp;
    private int wealth;
    private ArrayList<Plot> farmland;
    private ArrayList<InventoryItem> inventory;
    private ArrayList<Stealing> stolenCrops;
}
