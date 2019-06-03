package db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TableEnglish implements ITable {
    @Override public String getTableName() { return "english"; }
    @Override public Logger log() { return LogManager.getLogger(TableEnglish.class); }
    private Logger log = log();

    @Override
    public String getCreateTableString() {
        return "CREATE TABLE " + getTableName() + "( " +
                "row_id INT NOT NULL AUTO_INCREMENT, " +
                "en VARCHAR(100), " +
                "ru VARCHAR(100), " +
                "PRIMARY KEY(row_id));";
    }

    // заполняяем пустую таблицу данными
    @Override
    public void postCreateTable() {
        for (Integer i = 1; i <= 10; i++) {
            addRecord("eng" + i, "rus" + i);
        }
    }

    // constructor
    public TableEnglish() {
        log.trace("Constructor");
        init();
    }

    public Integer addRecord(String enString, String ruString) {
        if (!tableExists()) createTable();
        String sql = "INSERT INTO " + getTableName() + " (en, ru) VALUES ('" +
                enString + "', '" +
                ruString + "');";
        if (DBOperation.executeSQL(sql)) return 0;
        return -1;
    }
}