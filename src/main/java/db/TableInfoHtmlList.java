package db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;

public class TableInfoHtmlList {
    private static final Logger log = LogManager.getLogger(TableInfoHtmlList.class);
    private static String TABLE_NAME = "infohtml_list_temp";
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
        log.trace("Constructor");
        // если таблицы нет - создаём её и забиваем тестовыми данными
        if (!tableExists()) {
            createTable();

            addRecord("category1", "", 0, "", true);
            for (Integer i = 0; i < 4; i++) {
                addRecord("id" + i, "cat1-title " + i, i, "category1", false);
            }
            addRecord("category2", "", 1, "", true);
            for (Integer i = 5; i < 8; i++) {
                addRecord("id" + i, "cat2-title " + i, i, "category2", false);
            }
            addRecord("id4", "cat1-title 4", 4, "category1", false);
        }
    }

    public boolean addRecord(String id, String caption, Integer position, String parent, boolean isCategory) {
        if (!tableExists()) createTable();
        String record = "INSERT INTO " + TABLE_NAME + " (title, link_text, position, parent, category_flag) VALUES ('" +
                String.join("', '", id, caption, position.toString(), parent, (isCategory ? "1" : "0")) + "');";
        return DBOperation.executeSQL(record);
    }

    public String getAllRecords() {
        if (!tableExists()) return "Error! Info not found";
        ResultSet rs = DBOperation.getData("select title, link_text from " + TABLE_NAME + " order by position;");
        HashSet<String> resultArray = new HashSet<>();
        try {
            while (rs.next()) {
                resultArray.add(rs.getString("title") + ":" + rs.getString("link_text"));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        String[] sArr = new String[resultArray.size()];
        int i = 0;
        for (String s : resultArray)
            sArr[i++] = s;
        return String.join("~", sArr);
    }

    public void createTable() {
        if (tableExists()) return;

        log.debug("Creating table: " + TABLE_NAME);
        DBOperation.executeSQL(CREATE_TABLE_STRING);
    }

    public boolean tableExists() {
        return DBOperation.tableExists(TABLE_NAME);
    }
}
