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
        String sql = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userInfor.getUsername());
            statement.setString(2, userInfor.getPassword());
            statement.setString(3, userInfor.getRegion());
            statement.setString(4, userInfor.getGender());
            statement.setInt(5, userInfor.getAge());
            statement.setInt(6, 0);
            statement.setInt(7, 0);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(UserInfor userInfor) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userInfor.getUsername());
            ResultSet rs = statement.executeQuery();
            if(rs.next() && rs.getString("password").equals(userInfor.getPassword()) ) {
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



}