package com.booking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBConnection {

    private static final Logger logger =
            LoggerUtil.getLogger(DBConnection.class);

    private static final String URL =
            "jdbc:mysql://localhost:3306/hotel_booking_system";

    private static final String USER = "root";

    private static final String PASSWORD =
            "Sirini@2005";

    public static Connection getConnection()
            throws SQLException {

        try {
            Connection connection =
                    DriverManager.getConnection(
                            URL,
                            USER,
                            PASSWORD
                    );

            logger.info("Database connection established successfully.");

            return connection;

        } catch (SQLException e) {

            logger.severe(
                    "Database connection failed: "
                            + e.getMessage()
            );

            throw e;
        }
    }
}