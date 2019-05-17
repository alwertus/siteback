import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // тут всякое своё
//        TableInfoHtml dbHtml = new TableInfoHtml();
//        dbHtml.createTable();



//        dbHtml.showAllRecords();

        // запуск jetty сервера
        ServerStarter serverStarter = new ServerStarter(args);
        new Thread(serverStarter).start();
    }
}