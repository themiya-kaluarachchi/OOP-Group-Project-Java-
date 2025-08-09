package com.example.javaminiproject;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

/**
 * @author Kivilak Chathuranga
 */


public class MySQLConnection {
    private final String user;
    private final String password;
    private String url;
    private final Connection connection;

    public MySQLConnection() {
        Dotenv dotenv = Dotenv.load();
        this.user = dotenv.get("DB_USER");
        this.password = dotenv.get("DB_PASSWORD");
        this.url = dotenv.get("DB_URL");

        connection = getConnection();
    }

    private Connection getConnection() { // get connection to the database
        Connection connection = null;
        this.url = this.url + "seaexplorer";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            //System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("MySQL ERROR: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Name not found: " + e.getMessage());
        }

        return connection;
    }

    public RegionalInfo[] getRegionalInfo() {
        RegionalInfo[] regionalInfo = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        String sql = "SELECT * FROM details";

        try {
            statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery();
            int rows = 0;

            if(resultSet.last()) {
                rows = resultSet.getRow();
                resultSet.beforeFirst();
            }

            regionalInfo = new RegionalInfo[rows];

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String small_description = resultSet.getString("small_des");
                String description = resultSet.getString("des");
                String imageUrl = resultSet.getString("img_url");
                String district = resultSet.getString("district");
                String location = resultSet.getString("location");
                String type = resultSet.getString("type");
                double rating = resultSet.getDouble("rating");

                regionalInfo[resultSet.getRow() - 1] = new RegionalInfo(id, name, small_description, description, imageUrl, district, location, type, (float) rating);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //System.out.println(regionalInfo[0].toString());

        return regionalInfo;
    }

    public RegionalInfo[] getRegionalInfoByType(String type) {
         RegionalInfo[] regionalInfo = null;
         ResultSet resultSet = null;
         PreparedStatement statement = null;
         String sql = "SELECT * FROM details WHERE type = ?";

        try {
            statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, type);
            resultSet = statement.executeQuery();
            int rows = 0;

            if(resultSet.last()) {
                rows = resultSet.getRow();
                resultSet.beforeFirst();
            }

            regionalInfo = new RegionalInfo[rows];

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String small_description = resultSet.getString("small_des");
                String description = resultSet.getString("des");
                String imageUrl = resultSet.getString("img_url");
                String district = resultSet.getString("district");
                String location = resultSet.getString("location");
                String typeValue = resultSet.getString("type");
                double rating = resultSet.getDouble("rating");

                regionalInfo[resultSet.getRow() - 1] = new RegionalInfo(id, name, small_description, description, imageUrl, district, location, typeValue, (float) rating);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return regionalInfo;
    }
}
