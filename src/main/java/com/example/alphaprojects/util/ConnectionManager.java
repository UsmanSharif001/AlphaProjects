package com.example.alphaprojects.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection conn;

    private ConnectionManager(){

    }

    public static Connection getConnection(String db_url, String uid, String psw){

        if (conn != null) return conn;

        try{
            conn = DriverManager.getConnection(db_url,uid,psw);
        } catch (SQLException e) {
            System.out.println("Coulnd't connect to db");
            e.printStackTrace();
        }
        return conn;
    }

}
