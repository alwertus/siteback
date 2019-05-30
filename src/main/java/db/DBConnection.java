package db;

import help.ServerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DBConnection implements AutoCloseable {
    private static final Logger log = LogManager.getLogger(DBConnection.class);
    private final static String DB_URL = ServerConfig.getProperty("db_connection");
    private final static String DB_USER = ServerConfig.getProperty("db_user");
    private final static String DB_PASSWORD = ServerConfig.getProperty("db_pass");
    private static Connection connection = null;

    public static Connection openConnection() {
        if (connection != null) return connection;
        if (DB_URL == null) {
            log.error("Can not load DB URL from config file");
            return null;
        }

        log.trace("Try to open connection (" + DB_URL + ")");
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