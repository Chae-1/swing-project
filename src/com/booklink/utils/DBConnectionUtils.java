package com.booklink.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnectionUtils {

    private static final String PROPERTIES_ADDRESS = "src/com/properties/dbconnection.properties";

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(PROPERTIES_ADDRESS));
        return properties;
    }

    public static Connection getConnection() {
        try {
            Properties properties = loadProperties();
            String url = (String) properties.get("url");
            String username = (String) properties.get("username");
            String password = (String) properties.get("password");
            return DriverManager.getConnection(url, username, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
