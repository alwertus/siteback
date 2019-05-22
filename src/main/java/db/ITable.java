package db;

import org.apache.logging.log4j.Logger;

public interface ITable {
    String getTableName();
    String getCreateTableString();
    Logger log();

    // проверка существования таблицы
    default boolean tableExists() {
        boolean isTableExisit = DBOperation.tableExists(getTableName());
        log().trace("Table '" + getTableName() + "' is " + (isTableExisit ? "" : "not ") + "exisit");
        return isTableExisit;
    }

    // создание таблицы
    default void createTable() {
        if (tableExists()) return;

        log().debug("Creating table: " + getTableName());
        DBOperation.executeSQL(getCreateTableString());
    }

    // если таблицы нет - создаём её
    default void init() {
        if (!tableExists()) {
            log().info("Table " + getTableName() + " is not exisit");
            createTable();

            postCreateTable();
        }
    }

    // переопределить, если нужны действия после создания таблицы
    default void postCreateTable() {

    }
}
