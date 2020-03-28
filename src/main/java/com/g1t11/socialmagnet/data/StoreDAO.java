package com.g1t11.socialmagnet.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;

public class StoreDAO extends DAO {
    public StoreDAO(Database db) {
        super(db);
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

        try (PreparedStatement stmt = connection().prepareStatement(queryString);) {
            rs = stmt.executeQuery();
            while (rs.next()) {
                size = rs.getInt("count(*)");
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
        return size;
    }

    public Crop getStoreCrop(int index) {
        List<Crop> cropList = this.getStoreItem();
        Object[] cropArray = cropList.toArray();
        Crop crop = (Crop) cropArray[index - 1];
        return crop;
    }

    public boolean purchaseCrop(Farmer me, Crop crop, int amount) {
        boolean result = false;
        String queryString = "CALL purchaseCrop(?, ?, ?)";
        try (PreparedStatement stmt = connection().prepareStatement(queryString);) {
            stmt.setString(1, me.getUsername());
            stmt.setString(2, crop.getName());
            stmt.setInt(3, amount);

            stmt.execute();
            result = true;
        } catch (SQLException e) {
            result = false;
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            return result;
        }
    }
}