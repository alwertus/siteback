package db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TableEnglish implements ITable {
    @Override public String getTableName() { return "english"; }
    @Override public Logger log() { return LogManager.getLogger(TableEnglish.class); }

    @Override
    public String getCreateTableString() {
        return null;
    }
}
