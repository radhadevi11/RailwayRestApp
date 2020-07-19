package com.radha.railwayrest.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public Connection getConnection()  {
        String url = "jdbc:mysql://localhost/competition";
        try {
            Connection connection = DriverManager.getConnection(url, "root", "radha");
            return connection;
        }
        catch ( SQLException e){
            System.out.println("Could not connect to DB using url: " + url);
            throw new RuntimeException("Could not connect to database", e);
        }
    }
}
