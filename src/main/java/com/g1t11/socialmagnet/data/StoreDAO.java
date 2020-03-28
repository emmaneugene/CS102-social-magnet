package com.g1t11.socialmagnet.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;

public class StoreDAO extends DAO {
    public StoreDAO(Database db) {
        super(db);
    }

    public List<Crop> getStoreItems() {
        ResultSet rs = null;

        List<Crop> storeItems = new ArrayList<>();

        String queryString = "CALL get_store_items()";

        try (PreparedStatement stmt = connection().prepareStatement(queryString);) {
            rs = stmt.executeQuery();

            while (rs.next()) {
                Crop c = new Crop(
                    rs.getString("crop_name"),
                    rs.getInt("cost"),
                    rs.getInt("minutes_to_harvest"),
                    rs.getInt("xp"),
                    rs.getInt("min_yield"),
                    rs.getInt("max_yield"),
                    rs.getInt("sale_price"));
                storeItems.add(c);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return storeItems;
    }

    public boolean purchaseCrop(Farmer me, Crop crop, int amount) {
        ResultSet rs = null;

        boolean isSuccessful = false;

        String queryString = "CALL purchase_crop(?, ?, ?)";
        try (PreparedStatement stmt = connection().prepareStatement(queryString);) {
            stmt.setString(1, me.getUsername());
            stmt.setString(2, crop.getName());
            stmt.setInt(3, amount);

            rs = stmt.executeQuery();
            rs.next();

            isSuccessful = rs.getBoolean("purchase_success");
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
        return isSuccessful;
    }
}