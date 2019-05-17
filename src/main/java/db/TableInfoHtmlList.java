package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;

public class TableInfoHtmlList {
    private static String TABLE_NAME = "infohtml_list";
    private static String CREATE_TABLE_STRING = "CREATE TABLE " + TABLE_NAME +
            "( row_id INT NOT NULL AUTO_INCREMENT, " +
            "title VARCHAR(100) NOT NULL, " +
            "link_text VARCHAR(100), " +
            "position INT, " +
            "parent VARCHAR(100), " +
            "category_flag TINYINT(1), " +
            "PRIMARY KEY(row_id));";

    // constructor
    public TableInfoHtmlList() {
        // если таблицы нет - создаём её и забиваем тестовыми данными
        if (!tableExists()) {
            createTable();

            addRecord("category1", "", 0, "", true);
            for (Integer i = 0; i < 5; i++) {
                addRecord("nam" + i, "link" + i, i, "category1", false);
            }
        }
    }

    public void addRecord(String htmlName, String linkText, Integer position, String parent, boolean isCategory) {
        if (!tableExists()) createTable();
        String record = "INSERT INTO " + TABLE_NAME + " (title, link_text, position, parent, category_flag) VALUES ('" +
                String.join("', '", htmlName, linkText, position.toString(), parent, (isCategory ? "1" : "0")) + "');";
        System.out.println("SQL: " + record);
        DBOperation.executeSQL(record);
    }

    public String getAllRecords() {
        if (!tableExists()) return "Error! Info not found";
        ResultSet rs = DBOperation.getData("select title, link_text from " + TABLE_NAME);
        HashSet<String> resultArray = new HashSet<>();
        try {
            while (rs.next()) {
                resultArray.add(rs.getString("title") + ":" + rs.getString("link_text"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] sArr = new String[resultArray.size()];
        int i = 0;
        for (String s : resultArray)
            sArr[i++] = s;
        return String.join(",", sArr);
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
