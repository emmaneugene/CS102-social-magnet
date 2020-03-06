package com.g1t11.socialmagnet.util.debug;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TablePrinter {
    Connection conn;

    public TablePrinter(Connection conn) {
        this.conn = conn;
    }

    public void printTable(String tableName, int maxRows) {
        if (tableName == null || tableName.length() == 0) return;
        if (maxRows <= 0) return;

        System.out.println(tableName);
        ResultSet rs = getResultSet(tableName, maxRows);
        List<Column> columns = getColumns(rs);
        printColumns(columns);
    }

    private ResultSet getResultSet(String tableName, int maxRows) {
        assert(tableName != null && tableName.length() != 0);
        assert(maxRows > 0);

        String query = String.format("SELECT * FROM %s LIMIT %d;", tableName, maxRows);

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
        }

        return rs;
    }

    private List<Column> getColumns(ResultSet rs) {
        if (rs == null) return null;

        ResultSetMetaData rsmd = null;
        List<Column> columns = null;

        try {
            rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();
            columns = new ArrayList<>(columnCount);

            for (int i = 1; i <= columnCount; i++) {
                Column c = new Column(rsmd.getColumnLabel(i));
                columns.add(c);
            }

            while (rs.next()) {
                for (int colIndex = 0; colIndex < columnCount; colIndex++) {
                    Column c = columns.get(colIndex);
                    // rs.getString is 1 indexed
                    String value = rs.getString(colIndex + 1);

                    if (value == null) value = "NULL";

                    // set the column to be as wide as the widest cell
                    if (c.getWidth() < value.length())
                        c.setWidth(value.length());

                    c.addValue(value);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
        }

        return columns;
    }

    private void printColumns(List<Column> columns) {
        if (columns == null) return;

        StringBuilder headerRow = new StringBuilder();
        StringBuilder rowSeparator = new StringBuilder();

        for (Column c : columns) {
            int width = c.getWidth();

            String name = c.getLabel();
            int diff = width - name.length();

            // Odd amount of spacing between header name and column space
            if (diff % 2 == 1) {
                width++;
                diff++;
                c.setWidth(width);
            }

            int paddingSize = diff / 2;
            String padding = " ".repeat(paddingSize + 1);

            headerRow
                .append("|")
                .append(padding)
                .append(name)
                .append(padding);
            rowSeparator
                .append("+")
                .append("-".repeat(width + 2));
        }
        headerRow.append("|");
        rowSeparator.append("+");

        System.out.println(rowSeparator);
        System.out.println(headerRow);
        System.out.println(rowSeparator.toString().replaceAll("-", "="));

        for (int rowIndex = 0; rowIndex < columns.get(0).getRowCount(); rowIndex++) {
            for (Column c : columns) {
                int width = c.getWidth();
                int paddingSize = width - c.getValue(rowIndex).length();
                String padding = " ".repeat(paddingSize + 1);
                System.out.printf("| %s%s", c.getValue(rowIndex), padding);
            }
            System.out.println("|");
        }
        System.out.println(rowSeparator);
    }
}

class Column {
    private String label;

    private int width = 0;

    private List<String> values = new ArrayList<>();

    public Column(String label) {
        this.label = label;
        this.width = label.length();
    }

    public String getLabel() {
        return label;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int newValue) {
        width = newValue;
    }

    /**
     * Adds a String representation of a value to this column.
     */
    public void addValue(String value) {
        values.add(value);
    }

    public String getValue(int i) {
        return values.get(i);
    }

    public int getRowCount() {
        return values.size();
    }
}
