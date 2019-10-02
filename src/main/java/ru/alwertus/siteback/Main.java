package ru.alwertus.siteback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.alwertus.siteback.common.Global;

// Version: 2.0
public class Main {
    private static Logger log = LogManager.getLogger(Main.class.getName());


    // start point
    public static void main(String[] args) {
        log.info("====================== START ======================");
        log.trace(Global.Config.getFilename());
        /*
        DELETE THIS INFO
        192.168.1.8:3306
        alwertus
        3574

        log.trace(Config.getProp("db_connection", "jdbc:mysql://<IP_ADDRESS_DATABASE_CHANGE_ME>:<PORT_CHANGE_ME>/website?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow"));
        log.trace(Config.getProp("db_user", "<USER_NAME_DATABASE_CHANGE_ME>"));
        log.trace(Config.getProp("db_pass", "<PASSWORD_DATABASE_CHANGE_ME>"));*/

    }
}