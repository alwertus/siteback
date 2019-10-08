package old;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Deprecated
public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("MAIN");
        // запуск jetty сервера
//        ServerStarter serverStarter = new ServerStarter(args);
//        new Thread(serverStarter).start();
    }
}