package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOperation {

    public static ResultSet getData(String sql) {
        ResultSet rs = null;
        Statement st = null;
        try {
            st = DBConnection.getConnection().createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void executeSQL(String sql) {
        Statement st = null;
        try {
            st = DBConnection.getConnection().createStatement();
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean tableExists(String tableName) {
        ResultSet rs = DBOperation.getData("show tables like '" + tableName + "';");
        try {
            while (rs.next())
                return true;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
