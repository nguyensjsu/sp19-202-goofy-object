package com.goofyobject.tetris.database;

import org.springframework.stereotype.Repository;
import java.sql.*;

@Repository
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
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
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

    public boolean saveUser(UserInfo userInfo) {
        String sql = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userInfo.getUsername());
            statement.setString(2, userInfo.getPassword());
            statement.setString(3, userInfo.getRegion());
            statement.setString(4, userInfo.getGender());
            statement.setInt(5, userInfo.getAge());
            statement.setInt(6, 0);
            statement.setInt(7, 0);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(UserInfo userInfo) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userInfo.getUsername());
            ResultSet rs = statement.executeQuery();
            if(rs.next() && rs.getString("password").equals(userInfo.getPassword()) ) {
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