package com.booklink.utils;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;

public class DBDataTypeMatcher {
    public static Clob stringToClob(Connection connection, String data) {
        if (data == null) {
            return null;
        }

        Clob clob = null;
        try {
            clob = connection.createClob();
            clob.setString(1, data);
            return clob;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
