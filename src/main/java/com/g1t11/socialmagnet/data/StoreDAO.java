package com.g1t11.socialmagnet.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.User;

public class StoreDAO extends DAO {
    public StoreDAO(Database db) {
        super(db);
    }

    public Farmer getFarmer(User user) {
        ResultSet rs = null;
        Farmer f = null;

        String queryString = "CALL get_farmer_detail(?)";

        try (PreparedStatement stmt = connection().prepareStatement(queryString);) {
            stmt.setString(1, user.getUsername());

            rs = stmt.executeQuery();
            rs.next();

            f = new Farmer(user.getUsername(), user.getFullname(), rs.getInt("xp"), rs.getInt("wealth"),
                    rs.getInt("wealth_rank"));
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
        }

        return f;
    }

    public List<Crop> getStoreItem() {
        ResultSet rs = null;

        Crop[] emptyCrops = new Crop[this.getStoreItemSize()];
        Arrays.fill(emptyCrops, new Crop()); 
        List<Crop> storeItem = new ArrayList<>(Arrays.asList(emptyCrops));

        String queryString = "CALL get_store_item()";

        try (PreparedStatement stmt = connection().prepareStatement(queryString);) {
            rs = stmt.executeQuery();
            int index = 0;
            while (rs.next()) {
                Crop c = new Crop(rs.getString("crop_name"), rs.getInt("cost"), rs.getInt("minutes_to_harvest"),
                        rs.getInt("xp"), rs.getInt("min_yield"), rs.getInt("max_yield"), rs.getInt("sale_price"));
                storeItem.set(index, c);
                index++;
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
        }

        return storeItem;
    }
    public int getStoreItemSize() {
        ResultSet rs = null;

        int size = 0;
        String queryString = "CALL get_store_item_size()";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            rs = stmt.executeQuery();
            while (rs.next()) {
                size = rs.getInt("count(*)");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try {
                if (rs !=null)
                    rs.close();
            } catch (SQLException e) {

            }
        }
        return size;
    }

    public Crop getStoreCrop(int index) {
        List<Crop> cropList = this.getStoreItem();
        Object[] cropArray = cropList.toArray();
        Crop crop = (Crop) cropArray[index - 1];
        return crop;
    }

    public boolean isAbleToPurchase (Farmer me, Crop crop, int amount) {
        int currentWealth = me.getWealth();
        int purchaseCost = amount * crop.getCost();
        if (currentWealth >= purchaseCost) {
            return true;
        }
        return false;
    }

    public void purchaseCrop(Farmer me, Crop crop, int amount) {
        int purchaseCost = amount * crop.getCost();
        String queryString = "CALL update_wealth_after_purchase(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, me.getUsername());
            stmt.setInt(2, purchaseCost);

            stmt.execute();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    public void updateInventory(Farmer me, Crop crop, int amount) {
        String queryString = "CALL update_inventory(?, ?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, me.getUsername());
            stmt.setString(2, crop.getName());
            stmt.setInt(3, amount);

            stmt.execute();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }
}