import db.DBConnection;
import db.DBOperation;
import db.InfoHtml;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // тут всякое своё
        ResultSet rs = DBOperation.getData("select * from info_html");
        //System.out.println("ID NAME AGEE");
        System.out.println("---------------------");
        while (rs.next()) {
            String id = rs.getString("row_id");
            String name = rs.getString("title");
            Date age = rs.getDate("create_date");
            String html = rs.getString("html");
            System.out.println(String.join(" - ", id, name, age.toString(), html));
        }

        //InfoHtml info = new InfoHtml();
        //System.out.println(info.tableExists()? "существует" : "не существует");


        //DBConnection conn = new DBConnection();
        // запуск jetty сервера
        /*ServerStarter serverStarter = new ServerStarter(args);
        new Thread(serverStarter).start();*/
    }
}
