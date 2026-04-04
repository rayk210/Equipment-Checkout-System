package com.ecs.ecs_api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	private static DBConnect instance;
	private static Connection connection;
	
	private static final String url = "jdbc:mysql://localhost:3306/ceis400courseproject";
    private static final String username = "root";
    private static final String password = "devry123";
    
    private DBConnect() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConnect getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnect();
        } else if (instance.getConnection().isClosed()) {
            instance = new DBConnect();
        }
        return instance;
    }
}
