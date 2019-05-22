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
        Integer it1 = addRecord(0, 0, new Date(), "item1", "Text 1");
            Integer it11 = addRecord(it1, 0, new Date(), "item1.1", "Text 1.1");
                addRecord(it11, 0, new Date(), "item1.1.1", "Text 1.1.1");
            addRecord(it1, 0, new Date(), "item1.2", "Text 1.2");
        Integer it2 = addRecord(0, 0, new Date(), "item2", "Text 2");
            addRecord(it2, 0, new Date(), "item2.1", "Text 2.1");
        Integer it3 = addRecord(0, 0, new Date(), "item3", "Text 3");
            addRecord(it3, 0, new Date(), "item3.1", "Text 3.1");

    }

    // constructor
    public TableInfo() {
        log.trace("Constructor");
        init();
        for (int i = 0; i < 10; i++) {
            getBranch(i);
        }
    }

    public ResultSet getBranch(Integer id) {
        if (!tableExists()) return null;
        String sql = "select T.row_id, T.weight, T.created, T.title, (select count(title) as 'cnt' from info T1 where T1.par_id=T.row_id) as 'child_count' from info T where T.par_id=" + id + ";";
        ResultSet rs = DBOperation.getData(sql);
        /*try {
            log.warn("!!! ----- branch ID=" + id + " -----");
            while (rs.next()) {
                log.warn("!!! "+ rs.);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        return rs;
    }

    public Integer getId(Date date, String title) {
        if (!tableExists()) return -1;
        ResultSet rs = DBOperation.getData("select row_id from " + getTableName() +
                " where created = STR_TO_DATE('" + ServerConfig.SIMPLE_DATE_FORMAT.format(date) + "', '" + ServerConfig.BD_DATE_FORMAT_STRING + "') AND title = '" + title + "';");
        try {
            while (rs.next()) {
                return rs.getInt("row_id");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return -1;
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

    public String getHTML(String id) {
        String sResult = "Info not found";
        ResultSet rs = DBOperation.getData("select html from " + getTableName() + " where row_id='" + id + "'");
        try {
            while (rs.next()) {
                return rs.getString("html");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return sResult;
    }
}