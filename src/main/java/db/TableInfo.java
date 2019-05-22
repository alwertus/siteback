package db;

import help.ServerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TableInfo implements ITable{
    @Override public Logger log() { return LogManager.getLogger(TableInfo.class); }
    @Override public String getTableName() { return "info"; }
    private Logger log = log();
    @Override public String getCreateTableString() {
        return "CREATE TABLE " + getTableName() + "( " +
                "row_id INT NOT NULL AUTO_INCREMENT, " +
                "par_id INT NOT NULL, " +
                "weight INT, " +
                "created DATETIME, " +
                "title VARCHAR(100) NOT NULL, " +
                "html TEXT, " +
                "PRIMARY KEY(row_id));";
    }

    @Override
    public void postCreateTable() {         // заполняяем пустую таблицу данными
        /**
          item 1
            item 1.1
               item 1.1.1
            item 1.2
          item 2
            item 2.1
          item 3
            item 3.1
         */
        Integer rec1 = addRecord(0, 0, new Date(), "item1", "Text 1");

    }

    // constructor
    public TableInfo() {
        log.trace("Constructor");
        init();
    }

    public Integer getId(Date date, String title) {
        if (!tableExists()) return -1;
        ResultSet rs = DBOperation.getData("select row_id from " + getTableName() +
                " where created = STR_TO_DATE('" + ServerConfig.SIMPLE_DATE_FORMAT.format(date) + "', '" + ServerConfig.BD_DATE_FORMAT_STRING + "' AND title = "
        try {
            while (rs.next()) {
                String id = ((Integer)rs.getInt("row_id")).toString();
                String title = rs.getString("title");
                String date = ServerConfig.SIMPLE_DATE_FORMAT.format(new Date(rs.getTimestamp("create_date").getTime()));
                String html = rs.getString("html");
                System.out.println(String.join(" - ", id, title, date, html));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Integer addRecord(Integer par_id, Integer weight, Date created, String title, String html) {
        if (!tableExists()) createTable();
        String record = "INSERT INTO " + getTableName() + " (par_id, weight, created, title, html) VALUES (" +
                par_id + "," +
                weight + "," +
                "STR_TO_DATE('" + ServerConfig.SIMPLE_DATE_FORMAT.format(created) + "', '" + ServerConfig.BD_DATE_FORMAT_STRING + "'), '" +
                title + "', '" +
                html + "');";
        if (DBOperation.executeSQL(record))
            return getId(created, title);
        else
            return -1;
    }
}
