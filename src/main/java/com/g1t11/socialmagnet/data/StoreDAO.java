package com.g1t11.socialmagnet.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.Crop;

public class StoreDAO extends DAO {
    public StoreDAO(Database db) {
        super(db);
    }

    /**
     * Get all currently available crops on the database.
     * @return A list of all available crops.
     */
    public List<Crop> getStoreItems() {
        ResultSet rs = null;

        List<Crop> storeItems = new ArrayList<>();

        String queryString = "CALL get_store_items()";

        try (PreparedStatement stmt = conn().prepareStatement(queryString);) {
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

    /**
     * Purchase an amount of crops from the store and add them to the user's
     * inventory, and update the user's wealth.
     * @param buyerName The user who is buying crops.
     * @param cropName The crop to purchase.
     * @param amount The number of bags of seeds to purchase.
     * @return True if the purchase was successful, or false if it was not.
     */
    public boolean purchaseCrop(String buyerName, String cropName, int amount) {
        ResultSet rs = null;

        boolean isSuccessful = false;

        String queryString = "CALL purchase_crop(?, ?, ?)";
        try (PreparedStatement stmt = conn().prepareStatement(queryString);) {
            stmt.setString(1, buyerName);
            stmt.setString(2, cropName);
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
