package com.g1t11.socialmagnet.data;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.model.social.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestFarmerDAO {
    private FarmerDAO farmerDAO;

    Farmer me = new Farmer("adam", "Adam Levine", 1500, 1300, 3);

    Crop papaya =     new Crop("Papaya",     20, 30,  8, 75, 100, 15);
    Crop pumpkin =    new Crop("Pumpkin",    30, 60,  5, 5,  200, 20);
    Crop watermelon = new Crop("Watermelon", 50, 240, 1, 5,  800, 10);

    @Before
    public void initDAO() {
        Database db = new Database();
        db.establishConnection();
        farmerDAO = new FarmerDAO(db);
    }

    @Test
    public void testGetFarmer() {
        Farmer expected = new Farmer("adam", "Adam Levine", 1500, 1300, 3);

        Farmer actual = farmerDAO.getFarmer(new User("adam", "Adam Levine"));

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetPlots() {
        List<Plot> expected = new ArrayList<>(List.of(
            new Plot(papaya),
            new Plot(pumpkin),
            new Plot(watermelon),
            new Plot(),
            new Plot()
        ));

        List<Plot> actual = farmerDAO.getPlots(new Farmer("britney", "Britney Spears"));

        for (int i = 0; i < actual.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test(expected = DatabaseException.class)
    public void testPlantCrop() {
        farmerDAO.plantCrop(me, 6, "Papaya");
        // Duplicate entry
        farmerDAO.plantCrop(me, 6, "Papaya");
    }

    @Test(expected = DatabaseException.class)
    public void testPlantCropInvalidPlot() {
        farmerDAO.plantCrop(me, 9, "Papaya");
    }

    public void testGetInventoryCrops() {
        farmerDAO.getInventoryCrops(me);
    }
}
