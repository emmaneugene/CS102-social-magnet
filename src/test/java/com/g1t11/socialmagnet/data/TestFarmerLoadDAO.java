package com.g1t11.socialmagnet.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.model.social.UserNotFoundException;

import org.junit.Assert;
import org.junit.Test;

public class TestFarmerLoadDAO extends TestDAO {
    private FarmerLoadDAO farmerLoadDAO;

    public static final Crop papaya     = new Crop("Papaya",     20, 30,  8,  75, 100, 15);
    public static final Crop pumpkin    = new Crop("Pumpkin",    30, 60,  5,   5, 200, 20);
    public static final Crop sunflower  = new Crop("Sunflower",  40, 120, 20, 15, 20,  40);
    public static final Crop watermelon = new Crop("Watermelon", 50, 240, 1,   5, 800, 10);

    public TestFarmerLoadDAO() {
        farmerLoadDAO = new FarmerLoadDAO(db);
    }

    @Test
    public void testGetFarmer() {
        Farmer expected = new Farmer("adam", "Adam Levine", 11000, 15000, 1);

        Farmer actual = farmerLoadDAO.getFarmer("adam");

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetNonExistentFarmer() {
        farmerLoadDAO.getFarmer("yankee");
    }

    @Test
    public void testGetInventoryCrops() {
        Map<String, Integer> expected = new LinkedHashMap<>(Map.of("Papaya", 1, "Watermelon", 2));
        Map<String, Integer> actual = farmerLoadDAO.getInventoryCrops("britney");
        
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetInventoryEmpty() {
        Map<String, Integer> actual = farmerLoadDAO.getInventoryCrops("charlie");
        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void testGetPlots() {
        List<Plot> expected = new ArrayList<>(List.of(
            new Plot(papaya),
            new Plot(pumpkin),
            new Plot(watermelon),
            new Plot(),
            new Plot(),
            new Plot()
        ));

        List<Plot> actual = farmerLoadDAO.getPlots("britney", 6);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getGiftCountToday() {
        int expected = 5;
        int actual = farmerLoadDAO.getGiftCountToday("charlie");
        Assert.assertEquals(expected, actual);
    }
}
