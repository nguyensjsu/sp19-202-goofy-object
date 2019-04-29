package db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Date;

import javax.validation.constraints.Null;

// import db.DBConnection;
// import external.ExternalAPI;
// import external.ExternalAPIFactory;

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

    public boolean saveUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        if (checkUser(username)) {
            try {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);
                statement.execute();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
        return false;}
    }

    public boolean checkUser(String username) {
        Set<String> eventIdSet = new HashSet<>();
        String sql = "SELECT userid FROM users where username = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet != null){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkUserLogin(String username, String password) {
        String sql = "SELECT username FROM users WHERE username =? and password =?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveUserStats(String username, Date gameTime, int win ){
        String sql = "INSERT INTO histroy (userid, username, timestamp, game_flag, win_flag) VALUES (?, ?, ?, ?, ?)";
        int userid = getUserId(username).iterator().next();
        try {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, userid);
                statement.setString(2, username);
                statement.setDate(3, gameTime);
                statement.setInt(4, 1);
                statement.setInt(5, win_flag);
                statement.execute();
                return true;
            } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Set<Integer> getUserId(String username) {
        Set<Integer> idSet = new HashSet<>();
        try {
            String sql = "SELECT userid FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                idSet.add(rs.getInt("userid"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return idSet;
    }
}