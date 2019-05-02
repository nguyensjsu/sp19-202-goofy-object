package com.goofyobject.tetris.database;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;


public class MySQLConnection {
    private static MySQLConnection instance;
    private Connection conn = null;

    public static MySQLConnection getInstance() {
        if (instance == null) {
            instance = new MySQLConnection();
        }
        return instance;
    }

    private MySQLConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(MySQLDBUtil.URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public boolean saveUser(UserInfor userInfor) {
        String sql = "INSERT INTO user VALUES (?, ?, ? )";

        return true;
    }



}