package db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoHtml {

    public void createTable() {
        String createTableString =
                "CREATE TABLE info_html\n" +
                "( row_id varchar2(10) NOT NULL,\n" +
                "  title varchar2(100) NOT NULL,\n" +
                "  create_date date NOT NULL,\n" +
                "  html long,\n" +
                "  primary key(row_id)\n" +
                ");";
        if (DBOperation.tableExists("info_html")) return;
        System.out.println("creating table");
    }

    public boolean tableExists() {
        return DBOperation.tableExists("info_html");
    }

}
//http://oracleplsql.ru/datatypes.html