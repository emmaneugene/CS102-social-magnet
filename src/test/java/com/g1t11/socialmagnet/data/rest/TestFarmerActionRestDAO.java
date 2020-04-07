package com.g1t11.socialmagnet.data.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.g1t11.socialmagnet.data.DatabaseException;
import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.model.farm.StealingRecord;

import org.junit.Assert;
import org.junit.Test;

public class TestFarmerActionRestDAO {
    private FarmerLoadRestDAO farmerLoadDAO;
    private FarmerActionRestDAO farmerActionDAO;
    private ThreadActionRestDAO threadActionDAO;

    public static final Crop papaya
            = new Crop("Papaya",     20, 30,  8,  75, 100, 15);
    public static final Crop pumpkin
            = new Crop("Pumpkin",    30, 60,  5,   5, 200, 20);
    public static final Crop sunflower
            = new Crop("Sunflower",  40, 120, 20, 15, 20,  40);
    public static final Crop watermelon
            = new Crop("Watermelon", 50, 240, 1,   5, 800, 10);

    public TestFarmerActionRestDAO() {
        farmerLoadDAO = new FarmerLoadRestDAO();
        farmerActionDAO = new FarmerActionRestDAO();
        threadActionDAO = new ThreadActionRestDAO();
    }

    @Test
    public void testPlantAndClearCrops() {
        testPlantCrop();
        testClearCrop();
    }

    private void testPlantCrop() {
        List<Plot> expected
                = new ArrayList<>(farmerLoadDAO.getPlots("adam", 6));
        expected.set(4, new Plot(watermelon));

        farmerActionDAO.plantCrop("adam", 5, "Watermelon");

        List<Plot> actual = farmerLoadDAO.getPlots("adam", 6);

        Assert.assertEquals(expected, actual);
    }

    private void testClearCrop() {
        int expectedWealth = farmerLoadDAO.getFarmer("adam").getWealth();

        List<Plot> expectedPlots
                = new ArrayList<>(farmerLoadDAO.getPlots("adam", 6));
        expectedPlots.set(4, new Plot());

        farmerActionDAO.clearPlot("adam", 5);

        int actualWealth = farmerLoadDAO.getFarmer("adam").getWealth();
        List<Plot> actualPlots = farmerLoadDAO.getPlots("adam", 6);

        Assert.assertEquals(expectedWealth, actualWealth);
        Assert.assertEquals(expectedPlots, actualPlots);
    }

    @Test
    public void testClearWiltedCrop() {
        int expectedWealth = farmerLoadDAO.getFarmer("frank").getWealth() - 5;

        List<Plot> expectedPlots
                = new ArrayList<>(farmerLoadDAO.getPlots("frank", 5));
        expectedPlots.set(2, new Plot());

        farmerActionDAO.clearPlot("frank", 3);

        int actualWealth = farmerLoadDAO.getFarmer("frank").getWealth();
        List<Plot> actualPlots = farmerLoadDAO.getPlots("frank", 5);

        Assert.assertEquals(expectedWealth, actualWealth);
        Assert.assertEquals(expectedPlots, actualPlots);
    }

    @Test
    public void testPlantOnExistingCrop() {
        List<Plot> before = farmerLoadDAO.getPlots("adam", 6);

        try {
            farmerActionDAO.plantCrop("adam", 1, "Papaya");
        } catch (DatabaseException e) {}

        List<Plot> after = farmerLoadDAO.getPlots("adam", 6);

        Assert.assertEquals(before, after);
    }

    @Test
    public void testPlantWithoutInventory() {
        List<Plot> before = farmerLoadDAO.getPlots("adam", 6);

        try {
            farmerActionDAO.plantCrop("adam", 5, "Sunflower");
        } catch (DatabaseException e) {}

        List<Plot> after = farmerLoadDAO.getPlots("adam", 6);

        Assert.assertEquals(before, after);
    }

    @Test
    public void testPlantCropInvalidPlot() {
        List<Plot> before = farmerLoadDAO.getPlots("adam", 6);

        try {
            farmerActionDAO.plantCrop("adam", 9, "Papaya");
        } catch (DatabaseException e) {}

        List<Plot> after = farmerLoadDAO.getPlots("adam", 6);

        Assert.assertEquals(before, after);
    }

    @Test
    public void testHarvest() {
        Farmer charlieBefore = farmerLoadDAO.getFarmer("charlie");
        int expectedMaxPlotCount = charlieBefore.getMaxPlotCount() + 1;
        int expectedWealth = charlieBefore.getWealth() + 6250;
        List<Plot> expectedPlots = List.of(
                new Plot(), new Plot(pumpkin), new Plot(watermelon),
                new Plot(), new Plot(), new Plot(), new Plot()
        );

        farmerActionDAO.harvest("charlie");

        Farmer charlieAfter = farmerLoadDAO.getFarmer("charlie");
        int actualMaxPlotCount = charlieAfter.getMaxPlotCount();
        int actualWealth = charlieAfter.getWealth();
        List<Plot> actualPlots = farmerLoadDAO.getPlots("charlie", 7);

        Assert.assertEquals(expectedMaxPlotCount, actualMaxPlotCount);
        Assert.assertEquals(expectedWealth, actualWealth);
        Assert.assertEquals(expectedPlots, actualPlots);
    }

    @Test
    public void testSteal() {
        testStealFirst();
        testStealAgain();
    }

    private void testStealFirst() {
        // Stolen yield is randomly generated by database
        // In this test, stealing 1-3 Papayas and 4-20 Watermelons from elijah
        List<StealingRecord> minExpectedSteal = List.of(
            new StealingRecord(papaya, 1),
            new StealingRecord(watermelon, 4)
        );
        List<StealingRecord> maxExpectedSteal = List.of(
            new StealingRecord(papaya, 3),
            new StealingRecord(watermelon, 20)
        );
        Farmer dannyBefore = farmerLoadDAO.getFarmer("danny");

        List<StealingRecord> actualSteal
                = farmerActionDAO.steal("danny", "elijah");

        Farmer dannyAfter = farmerLoadDAO.getFarmer("danny");
        testStealerWealthAndXP(dannyBefore, dannyAfter,
                minExpectedSteal, maxExpectedSteal);

        int size = actualSteal.size();
        Assert.assertEquals(minExpectedSteal.size(), size);

        for (int i = 0; i < size; i++) {
            int actualXPGained = actualSteal.get(i).getTotalXpGained();
            int actualGoldGained = actualSteal.get(i).getTotalGoldGained();
            Assert.assertTrue(
                    minExpectedSteal.get(i).getTotalXpGained()
                            <= actualXPGained
                    && actualXPGained
                            <= maxExpectedSteal.get(i).getTotalXpGained());
            Assert.assertTrue(
                    minExpectedSteal.get(i).getTotalGoldGained()
                            <= actualGoldGained
                    && actualGoldGained
                            <= maxExpectedSteal.get(i).getTotalGoldGained());
        }
    }

    private void testStealerWealthAndXP(Farmer before, Farmer after,
            List<StealingRecord> minExpectedSteal,
            List<StealingRecord> maxExpectedSteal) {
        int minGoldGained = minExpectedSteal.stream().reduce(0,
            (sum,  next) -> sum + next.getTotalGoldGained(),
            (a,  b) -> a + b);
        int maxGoldGained = maxExpectedSteal.stream().reduce(0,
            (sum,  next) -> sum + next.getTotalGoldGained(),
            (a,  b) -> a + b);
        int minXPGained = minExpectedSteal.stream().reduce(0,
            (sum, next) -> sum + next.getTotalXpGained(),
            (a, b) -> a + b);
        int maxXPGained = maxExpectedSteal.stream().reduce(0,
            (sum, next) -> sum + next.getTotalXpGained(),
            (a, b) -> a + b);

        int minExpectedWealth = before.getWealth() + minGoldGained;
        int maxExpectedWealth = before.getWealth() + maxGoldGained;
        int minExpectedXP = before.getXp() + minXPGained;
        int maxExpectedXP = before.getXp() + maxXPGained;
        int actualWealth = after.getWealth();
        int actualXP = after.getXp();

        Assert.assertTrue(minExpectedWealth <= actualWealth
                && actualWealth <= maxExpectedWealth);
        Assert.assertTrue(minExpectedXP <= actualXP
                && actualXP <= maxExpectedXP);
    }

    private void testStealAgain() {
        // Should result in no yield stolen.
        Farmer dannyBefore = farmerLoadDAO.getFarmer("danny");
        int expectedWealth = dannyBefore.getWealth();
        int expectedXP = dannyBefore.getXp();

        List<StealingRecord> actualSteal
                = farmerActionDAO.steal("danny", "elijah");

        Farmer dannyAfter = farmerLoadDAO.getFarmer("danny");
        int actualWealth = dannyAfter.getWealth();
        int actualXP = dannyAfter.getXp();

        Assert.assertEquals(0, actualSteal.size());
        Assert.assertEquals(expectedWealth, actualWealth);
        Assert.assertEquals(expectedXP, actualXP);
    }

    @Test
    public void testStealAlready20Percent() {
         // Should result in no yield stolen.
        Farmer elijahBefore = farmerLoadDAO.getFarmer("elijah");
        int expectedWealth = elijahBefore.getWealth();
        int expectedXP = elijahBefore.getXp();

        List<StealingRecord> actualSteal
                = farmerActionDAO.steal("elijah", "danny");

        Farmer elijahAfter = farmerLoadDAO.getFarmer("elijah");
        int actualWealth = elijahAfter.getWealth();
        int actualXP = elijahAfter.getXp();

        Assert.assertEquals(0, actualSteal.size());
        Assert.assertEquals(expectedWealth, actualWealth);
        Assert.assertEquals(expectedXP, actualXP);
    }

    @Test
    public void testAcceptGifts() {
        Map<String, Integer> expected = farmerLoadDAO.getInventoryCrops("mark");
        // Adding onto existing crops in inventory.
        expected.put("Papaya", expected.get("Papaya") + 2);
        // Adding new crops to inventory.
        expected.put("Sunflower", 1);

        farmerActionDAO.acceptGifts("mark");
        Map<String, Integer> actual = farmerLoadDAO.getInventoryCrops("mark");

        Assert.assertEquals(expected, actual);

        // Accepted gifts canoot be accepted again.
        farmerActionDAO.acceptGifts("mark");
        Map<String, Integer> again = farmerLoadDAO.getInventoryCrops("mark");

        Assert.assertEquals(actual, again);
    }

    @Test
    public void testRejectGifts() {
        Map<String, Integer> expected
                = farmerLoadDAO.getInventoryCrops("nadia");
        // Adding onto existing crops in inventory.
        // Reject one papaya and one sunflower to test one rejected crop that
        // exists in inventory and one that does not.
        expected.put("Papaya", 1);

        threadActionDAO.deleteThread(26, "nadia");
        threadActionDAO.deleteThread(27, "nadia");
        farmerActionDAO.acceptGifts("nadia");
        Map<String, Integer> actual = farmerLoadDAO.getInventoryCrops("nadia");

        Assert.assertEquals(expected, actual);

        // Accepted gifts canoot be accepted again.
        farmerActionDAO.acceptGifts("mark");
        Map<String, Integer> again = farmerLoadDAO.getInventoryCrops("nadia");

        Assert.assertEquals(actual, again);
    }
}