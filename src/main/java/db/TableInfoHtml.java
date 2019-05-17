package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TableInfoHtml {
    private static String TABLE_NAME = "info_html";
    private static String CREATE_TABLE_STRING = "CREATE TABLE " + TABLE_NAME + "( row_id INT NOT NULL AUTO_INCREMENT, title VARCHAR(100) NOT NULL, create_date DATETIME, html TEXT, PRIMARY KEY(row_id));";
    private static String BD_DATE_FORMAT_STRING = "%Y.%m.%d %H:%i:%S";
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    // constructor
    public TableInfoHtml() {
        // если таблицы нет - создаём её и забиваем тестовыми данными
        if (!tableExists()) {
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
            e.printStackTrace();
        }
        return sResult;
    }

    public void addRecord(String title, Date date, String html) {
        if (!tableExists()) createTable();
        String record = "INSERT INTO " + TABLE_NAME + " (title, create_date, html) VALUES ('" +
                title + "', " +
                "STR_TO_DATE('" + DATE_FORMAT.format(date) + "', '" + BD_DATE_FORMAT_STRING + "'), '" +
                html + "');";
        DBOperation.executeSQL(record);
    }

    public void createTable() {
        if (tableExists()) return;

        System.out.println("creating table: " + TABLE_NAME);
        DBOperation.executeSQL(CREATE_TABLE_STRING);
    }

    public boolean tableExists() {
        return DBOperation.tableExists(TABLE_NAME);
    }
}


//http://oracleplsql.ru/datatypes.html