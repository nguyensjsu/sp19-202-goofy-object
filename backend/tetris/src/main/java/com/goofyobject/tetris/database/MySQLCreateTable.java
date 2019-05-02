package com.goofyobject.tetris.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLCreateTable {

    public static void main(String[] args) {
        try {
            // Ensure the driver is imported.
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // This is java.sql.Connection. Not com.mysql.jdbc.Connection.
            Connection conn = null;
            try {
                System.out.println("Connecting to \n" + MySQLDBUtil.URL);
                conn = DriverManager.getConnection(MySQLDBUtil.URL);
            } catch (SQLException e) {
                System.out.println("SQLException " + e.getMessage());
                System.out.println("SQLState " + e.getSQLState());
                System.out.println("VendorError " + e.getErrorCode());
            }
            if (conn == null) {
                return;
            }

            // Step 1 Drop tables in case they exist.
            Statement stmt = conn.createStatement();

            String sql = "DROP TABLE IF EXISTS user";
            stmt.executeUpdate(sql);


            // Step 2. Create new tables.
            sql = "CREATE TABLE user " + "(username VARCHAR(255) NOT NULL, "
                    + "password VARCHAR(255) NOT NULL, " + "region VARCHAR(255), " + "gender VARCHAR(255), "
                    + "age INT, " +"wins INT, "+"loses INT, "+ " PRIMARY KEY(username) )";
            stmt.executeUpdate(sql);

            System.out.println("Create Table is done successfully.");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
