package ru.alwertus.siteback.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.alwertus.siteback.common.Global;

import java.sql.*;

public class DB {
    private static final Logger log = LogManager.getLogger(DB.class);
    private final static String DB_URL = Global.Config.getProp("db_connection", "jdbc:mysql://<IP_ADDRESS_DATABASE_CHANGE_ME>:<PORT_CHANGE_ME>/website?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow");
    private final static String DB_USER = Global.Config.getProp("db_user", "<USER_NAME_DATABASE_CHANGE_ME>");
    private final static String DB_PASSWORD = Global.Config.getProp("db_pass", "<PASSWORD_DATABASE_CHANGE_ME>");
    private static Connection connection = null;

    // этот метод в дальнейшем можно сделать публичным и использовать
    private static Connection getConnection() {
        if (!isDbConnected()) {
            if (!openConnection()) {
                log.error("Connection can't be established");
            }
        }
        return connection;
    }

    // принудительно открыть соединение
    private static boolean openConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            if (!isPingSuccess()) {
                connection = null;
                return false;
            }
        } catch (SQLException e) {
            connection = null;
            return false;
        }
        return true;
    }

    // тестовый запрос в БД для проверки соединения
    private static boolean isDbConnected() {
        if (connection == null) return false;
        return isPingSuccess();
    }

    // -------------------------------------------------------------------------------------
    // обращения к бд
    // -------------------------------------------------------------------------------------

    private static boolean isPingSuccess() {
        final String CHECK_SQL_QUERY = "select 1;";
        try {
            final PreparedStatement statement = connection.prepareStatement(CHECK_SQL_QUERY);
            statement.execute();
            return true;
        } catch (SQLException | NullPointerException e) {
            log.error("Connection was lost (" + e.getMessage() + ")");
            return false;
        }
    }

    public static ResultSet getData(String sql) {
        log.trace("# Get ResultSet: " + sql);
        try {
            Connection conn = getConnection();
            if (conn == null) return null;
            Statement st = conn.createStatement();
            return st.executeQuery(sql);
        } catch (SQLException e) {
            log.error("Error get data: " + e.getMessage());
        }
        return null;
    }

    public static boolean executeSQL(String sql) {
        log.trace("# Execute SQL: " + sql);
        Statement st = null;
        try {
            Connection conn = getConnection();
            if (conn == null) return false;
            st = conn.createStatement();
            st.executeUpdate(sql);
            log.trace("<< Success execute SQL");
            return true;
        } catch (SQLException e) {
            log.error("<< Error execute SQL: " + e.getMessage());
            return false;
        }
    }
}
