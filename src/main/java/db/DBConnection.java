package db;

import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.util.Properties;

public class DBConnection implements AutoCloseable{
    private final static String DB_URL = "jdbc:oracle:thin:@127.0.0.1:1521/DB11G";
    private final static String DB_USER = "tret";
    private final static String DB_PASSWORD = "3574";
    private static OracleConnection connection = null;

    public static OracleConnection openConnection(){
        if (connection != null) return connection;
        Properties info = new Properties();
        info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
        info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
        info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");

        OracleDataSource ods = null;

        try {
            ods = new OracleDataSource();
            ods.setURL(DB_URL);
            ods.setConnectionProperties(info);
            connection = (OracleConnection) ods.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static OracleConnection getConnection() {
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

/* всякое про базу
            DatabaseMetaData dbmd = connection.getMetaData();
            System.out.println("Driver Name: " +                    dbmd.getDriverName());
            System.out.println("Driver Version: " +                 dbmd.getDriverVersion());
            System.out.println("Default Row Prefetch Value is: " +  connection.getDefaultRowPrefetch());
            System.out.println("Database Username is: " +           connection.getUserName());
 */