package ru.alwertus.siteback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.alwertus.siteback.common.Global;
import ru.alwertus.siteback.db.DbConnector;

// Version: 2.0
public class Main {
    private static Logger log = LogManager.getLogger(Main.class.getName());


    // start point
    public static void main(String[] args) {
        log.info("====================== START ======================");
        /*log.trace("Config -> " + Global.Config.getFilename());
        log.trace("Config -> " + Global.Config.getProp("Property1_config"));
        log.trace("Lang -> " + Global.Lang.getFilename());
        log.trace("Lang -> " + Global.Lang.getProp("Property1_language"));*/
        //TODO Delete this info
        /*
        DELETE THIS INFO
        192.168.1.8:3306
        alwertus
        3574
*/
        log.info(DbConnector.getConnection().toString());
        /*
        ServerStarter serverStarter = new ServerStarter();
        if (true)
            new Thread(serverStarter).start();                                                                          // Запуск Jetty Server в новом потоке
        else
            serverStarter.run();                                                                                        // Запуск Jetty Server
         */

    }
}