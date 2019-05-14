package db;

import java.sql.*;

public class DBConnection {
    String sTNSNames = "jdbc:oracle:thin:[tret/3574]@DB11G";
    String s = "";

    public DBConnection() throws SQLException, ClassNotFoundException {
        System.out.println("hello");
        Class.forName ("oracle.jdbc.OracleDriver");

        Connection conn = DriverManager.getConnection(sTNSNames);
        // @//machineName:port/SID,   userid,  password
        try {
            Statement stmt = conn.createStatement();
            try {
                ResultSet rset = stmt.executeQuery("select BANNER from SYS.V_$VERSION");
                try {
                    while (rset.next())
                        System.out.println (rset.getString(1));   // Print col 1
                }
                finally {
                    try { rset.close(); } catch (Exception ignore) {}
                }
            }
            finally {
                try { stmt.close(); } catch (Exception ignore) {}
            }
        }
        finally {
            try { conn.close(); } catch (Exception ignore) {}
        }

        System.out.println("bye");
    }
}
