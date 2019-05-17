package db;

import java.sql.*;

public class DBConnection implements AutoCloseable{
    private final static String DB_URL = "jdbc:mysql://192.168.43.185:3306/website?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
    private final static String DB_USER = "alwertus";
    private final static String DB_PASSWORD = "3574";
    private static Connection connection = null;

    public static Connection openConnection() {
        if (connection != null) return connection;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection() {
        return openConnection();
    }

    public static void closeConnection() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        closeConnection();
    }
}