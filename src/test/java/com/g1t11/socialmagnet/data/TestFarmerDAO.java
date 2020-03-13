package com.g1t11.socialmagnet.data;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestFarmerDAO {
    private FarmerDAO farmerDAO;

    @Before
    public void initDAO() {
        Database db = new Database();
        farmerDAO = new FarmerDAO(db.connection());
    }

    @Test
    public void testGetUser() {
        Farmer expected = new Farmer("adam", "Adam Levine", 1500, 1300, 3);

        Farmer actual = farmerDAO.getFarmer(new User("adam", "Adam Levine"));

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }
}
