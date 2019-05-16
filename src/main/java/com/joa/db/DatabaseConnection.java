package com.joa.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    //CONEXIÓN BD-PRODUCCIÓN REMOTA SERVIDOR SQL
    static String url = "jdbc:sqlserver://SQL2:1433;databaseName=Soccer;";
//    static String url = "jdbc:sqlserver://localhost:1433;databaseName=Soccer;";
    static String database = "Soccer;";
    static String user = "sa;";
    static String password = "##cbaCBA123##";
    
    public static Connection connect() {
        try {
            Class.forName(driver);

            String connectionUrl = url + "database=" + database + "user=" + user + "password=" + password;
            connection = DriverManager.getConnection(connectionUrl);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ERROR: " + e);
            return null;
        }
    }

}
