package db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DBConnection implements AutoCloseable {
    private static final Logger log = LogManager.getLogger(DBConnection.class);
    private final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/website?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
//    private final static String DB_URL = "jdbc:mysql://192.168.43.185:3306/website?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
    private final static String DB_USER = "alwertus";
    private final static String DB_PASSWORD = "3574";
    private static Connection connection = null;

    public static Connection openConnection() {
        if (connection != null) return connection;

        log.trace("Try to open connection");
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            log.trace("<< Success open connection");
        } catch (SQLException e) {
            log.error("<< Error open DB connection: " + e.getMessage());
            System.exit(0);
        }
        return connection;
    }

    public static Connection getConnection() {
        return openConnection();
    }

    public static void closeConnection() {
        log.trace(">> Try to close connection");
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            log.error("<< Error close connection: " + e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        closeConnection();
    }
}