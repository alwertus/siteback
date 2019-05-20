package db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOperation {
    private static final Logger log = LogManager.getLogger(DBOperation.class);

    public static ResultSet getData(String sql) {
        log.trace("Get ResultSet: " + sql);
        ResultSet rs = null;
        Statement st = null;
        try {
            st = DBConnection.getConnection().createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return rs;
    }

    public static boolean executeSQL(String sql) {
        log.trace(">> Execute SQL: " + sql);
        Statement st = null;
        try {
            st = DBConnection.getConnection().createStatement();
            st.executeUpdate(sql);
            log.trace("<< Success execute SQL");
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean tableExists(String tableName) {
        log.trace(">> Table '" + tableName + "' exisit ?");
        ResultSet rs = DBOperation.getData("show tables like '" + tableName + "';");
        try {
            while (rs.next()) {
                log.trace("<< Yes. Table exisit");
                return true;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        log.trace("<< No. Table is not exisit");
        return false;
    }
}
