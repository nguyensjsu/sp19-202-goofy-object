package com.goofyobject.tetris.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class database {

    private static String host = "";
    private static String userName = "";
    private static String password = "";

    public void connectToDB() {
        try {
            Connection conn = DriverManager.getConnection(host, userName, password);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

}