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
        try {
            DBConnection.getConnection().createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean tableExists(String tableName) {
        //ResultSet rs = DBOperation.getData("select * from user_tables where lower(table_name) like lower('" + tableName + "');");
        ResultSet rs = DBOperation.getData("select * from info_html;");

        try {
            while (rs.next()) return true;
        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

}
