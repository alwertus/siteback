package db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TableInfoHtml {
    private static final Logger log = LogManager.getLogger(TableInfoHtml.class);
    private static String TABLE_NAME = "info_html_temp";
    private static String CREATE_TABLE_STRING = "CREATE TABLE " + TABLE_NAME + "( row_id INT NOT NULL AUTO_INCREMENT, title VARCHAR(100) NOT NULL, create_date DATETIME, html TEXT, PRIMARY KEY(row_id));";
    private static String BD_DATE_FORMAT_STRING = "%Y.%m.%d %H:%i:%S";
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    // constructor
    public TableInfoHtml() {
        log.trace("Constructor");
        // если таблицы нет - создаём её и забиваем тестовыми данными
        if (!tableExists()) {
            log.info("Table " + TABLE_NAME + " is not exisit");
            createTable();
            addRecord("Общая инфа", new Date(), "<div><p>COMMON jsdflksjdlfjsdf test test test kkkkk zaza</div>");
            addRecord("ссылка 2", new Date(), "<div><p>2222test test test kkkkk zaza</div>");
            addRecord("ссылка 3", new Date(), "<div><p>3333test test test kkkkk zaza</div>");
            addRecord("title02", new Date(), "<div><p>teddassdgdfgdfgasdst test test kkkkk zaza</div>");
        }
    }

    public void showAllRecords() {
        if (!tableExists()) return;
        ResultSet rs = DBOperation.getData("select * from " + TABLE_NAME);
        try {
            while (rs.next()) {
                String id = ((Integer)rs.getInt("row_id")).toString();
                String title = rs.getString("title");
                String date = DATE_FORMAT.format(new Date(rs.getTimestamp("create_date").getTime()));
                String html = rs.getString("html");
                System.out.println(String.join(" - ", id, title, date, html));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getHTML(String titleName) {
        String sResult = "Info not found";
        ResultSet rs = DBOperation.getData("select * from " + TABLE_NAME + " where title like '" + titleName + "'");
        try {
            while (rs.next()) {
                return rs.getString("html");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return sResult;
    }

    public boolean addRecord(String title, Date date, String html) {
        if (!tableExists()) createTable();
        String record = "INSERT INTO " + TABLE_NAME + " (title, create_date, html) VALUES ('" +
                title + "', " +
                "STR_TO_DATE('" + DATE_FORMAT.format(date) + "', '" + BD_DATE_FORMAT_STRING + "'), '" +
                html + "');";
        return DBOperation.executeSQL(record);
    }

    public void createTable() {
        if (tableExists()) return;

        log.debug("Creating table: " + TABLE_NAME);
        DBOperation.executeSQL(CREATE_TABLE_STRING);
    }

    public boolean tableExists() {
        Boolean isTableExisit = DBOperation.tableExists(TABLE_NAME);
        log.trace("Table '" + TABLE_NAME + "' is " + (isTableExisit ? "" : "not ") + "exisit");
        return isTableExisit;
    }
}


//http://oracleplsql.ru/datatypes.html