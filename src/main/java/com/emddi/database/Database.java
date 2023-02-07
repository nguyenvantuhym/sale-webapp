package com.emddi.database;



import com.emddi.constants.StaticConfig;

import java.sql.Connection;
import java.sql.DriverManager;


public class Database {
    protected static Connection createConnection() {
        Connection connection = null;
        String DB_URL = String.format("jdbc:mysql://%s:3306/%s?useUnicode=yes&characterEncoding=UTF-8&characterSetResults=UTF-8", StaticConfig.dbUrl, StaticConfig.dbName);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, StaticConfig.dbUser, StaticConfig.dbPassword);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}
