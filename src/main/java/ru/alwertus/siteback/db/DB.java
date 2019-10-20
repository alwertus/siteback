package ru.alwertus.siteback.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.alwertus.siteback.common.Global;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DB {
    private static final Logger log = LogManager.getLogger(DB.class);
    private final static String DB_URL = Global.Config.getProp("db_connection", "jdbc:mysql://<IP_ADDRESS_DATABASE_CHANGE_ME>:3306/website?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow");
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
        log.debug("# Get ResultSet: " + sql);
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
        log.debug("# Execute SQL: " + sql);
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

    public static String getFieldValue(String sql, String fieldName) {
        log.trace("Get field value (" + fieldName + ")");
        try {
            ResultSet rs = DB.getData(sql);
            while (rs.next()) {
                return rs.getString(fieldName);
            }
        } catch (SQLException e) {
            return "";
        }
        return "";
    }

    // -------------------------------------------------------------------------------------
    // Для отладки (поулчить RS как строку)
    public static String rsToString(ResultSet rs) {
        try {
            ResultSetMetaData meta = null;
            meta = rs.getMetaData();

            //get column names
            int colCount = meta.getColumnCount();
            ArrayList<String> cols = new ArrayList<String>();
            for (int index=1; index<=colCount; index++)
                cols.add(meta.getColumnName(index));

            //fetch out rows
            ArrayList<HashMap<String,Object>> rows =
                    new ArrayList<HashMap<String,Object>>();

            while (rs.next()) {
                HashMap<String,Object> row = new HashMap<String,Object>();
                for (String colName:cols) {
                    Object val = rs.getObject(colName);
                    row.put(colName,val);
                }
                rows.add(row);
            }
            return rows.toString();
        } catch (Exception e) {
            log.error("Error convert RS to String: " + e.getMessage());
            return "Error convert";
        }
    }

    public static void tablestructureInit() {
        HashMap<String,String> SQL = new HashMap<>();
        SQL.put("1.0",
                "CREATE TABLE IF NOT EXISTS users ( " + String.join(", ", new String[]{
                        "row_id INT NOT NULL AUTO_INCREMENT",
                        "name VARCHAR(30)",
                        "login VARCHAR(30)",
                        "password VARCHAR(30)",
                        "role_id INT",
                        "created DATETIME",
                        "lastlogin DATETIME",
                        "sessionkey VARCHAR(50)",
                        "PRIMARY KEY(row_id)"
                }) +");");
        SQL.put("1.1", "INSERT IGNORE INTO users (row_id, name, login, password, role_id, created, lastlogin, sessionkey) VALUES " +
                "('1', 'GodMode', 'god', 'qweQWE123!@#', '1', now(), now(), '')," +
                "('2', 'Admin', 'admin', 'admin', '2', now(), now(), '')," +
                "('3', 'Alwertus', '1', '1', '3', now(), now(), '')," +
                "('4', 'Машулька', '2', '2', '3', now(), now(), '')," +
                "('5', 'Registered', '3', '3', '4', now(), now(), '')" +
                ";");

        SQL.put("2.0",
                "CREATE TABLE IF NOT EXISTS roles ( " + String.join(", ", new String[]{
                        "row_id INT NOT NULL AUTO_INCREMENT",
                        "name VARCHAR(30)",
                        "PRIMARY KEY(row_id)"
                }) +");");
        SQL.put("2.1", "INSERT IGNORE INTO roles (row_id, name) VALUES ('1','god'),('2','admin'),('3','owner'),('4','user'),('5','any');");
        SQL.put("3.0",
                "CREATE TABLE IF NOT EXISTS pagelist ( " + String.join(", ", new String[]{
                        "row_id INT NOT NULL AUTO_INCREMENT",
                        "title VARCHAR(30)",
                        "link VARCHAR(100)",
                        "owner_id INT",
                        "access_id INT",
                        "PRIMARY KEY(row_id)"
                }) +");");
        SQL.put("3.1", "INSERT IGNORE INTO pagelist (row_id, title, link, owner_id, access_id) VALUES " +
                "('1', 'Главная', 'mainpage', '2', '5')," +
                "('2', 'Володя', 'alwertus', '3', '3')," +
                "('3', 'Мащпулькэ', 'chevima', '4', '3')," +
                "('4', 'Зареганный', 'news', '2', '4')," +
                "('5', 'Админка', 'admin', '2', '2')," +
                "('6', 'GOD', 'god', '3', '1')" +
                ";");

        DB.executeSQL(SQL.get("1.0"));
        DB.executeSQL(SQL.get("1.1"));
        DB.executeSQL(SQL.get("2.0"));
        DB.executeSQL(SQL.get("2.1"));
        DB.executeSQL(SQL.get("3.0"));
        DB.executeSQL(SQL.get("3.1"));
    }
}
