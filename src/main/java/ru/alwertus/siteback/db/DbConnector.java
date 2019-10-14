package ru.alwertus.siteback.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.alwertus.siteback.common.Global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbConnector {
    private static final Logger log = LogManager.getLogger(DbConnector.class);
    private final static String DB_URL = Global.Config.getProp("db_connection", "jdbc:mysql://<IP_ADDRESS_DATABASE_CHANGE_ME>:<PORT_CHANGE_ME>/website?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow");
    private final static String DB_USER = Global.Config.getProp("db_user", "<USER_NAME_DATABASE_CHANGE_ME>");
    private final static String DB_PASSWORD = Global.Config.getProp("db_pass", "<PASSWORD_DATABASE_CHANGE_ME>");
    private static Connection connection = null;

    public static Connection getConnection() {
        if (!isDbConnected()) {
            if (DB_URL == null) {
                log.error("DB url is null");
                return null;
            }

            log.trace("Try to open connection (" + DB_URL + ")");
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                log.trace("<< Success open connection");
            } catch (SQLException e) {
                log.error("<< Error open DB connection: " + e.getMessage());
                connection = null;
            }
        }
        return connection;
    }

    public static boolean isDbConnected() {
        if (connection == null) return false;
        final String CHECK_SQL_QUERY = "SELECT 1";
        boolean isConnected = false;
        try {
            final PreparedStatement statement = connection.prepareStatement(CHECK_SQL_QUERY);
            isConnected = true;
        } catch (SQLException | NullPointerException e) {
            log.error("SQLException:" + e.getMessage());
        }
        return isConnected;
    }
}
