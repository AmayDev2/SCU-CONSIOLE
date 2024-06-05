package com.amay.scu.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String USERNAME ="postgres";
    private static final String PASSWORD ="Risab#123";
    private static final String URL = "jdbc:postgresql://localhost:5432/ccudb";

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnector.class);

    private static Connection connection = null;


    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                LOGGER.error("Error creating database connection: {}", e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            LOGGER.error("Error closing database connection: {}", e.getMessage());
        }
    }
}