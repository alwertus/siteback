import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("MAIN");
        // тут всякое своё
//        TableInfoHtml dbHtml = new TableInfoHtml();
//        dbHtml.createTable();



//        dbHtml.showAllRecords();
        // запуск jetty сервера
        ServerStarter serverStarter = new ServerStarter(args);
        new Thread(serverStarter).start();
    }
}