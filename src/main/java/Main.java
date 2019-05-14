import db.DBConnection;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // тут всякое своё
        DBConnection conn = new DBConnection();
        // запуск jetty сервера
        /*ServerStarter serverStarter = new ServerStarter(args);
        new Thread(serverStarter).start();*/
    }
}
