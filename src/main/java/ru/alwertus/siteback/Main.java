package ru.alwertus.siteback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.alwertus.siteback.db.DB;
import ru.alwertus.siteback.db.Users;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Version: 2.0
public class Main {
    private static Logger log = LogManager.getLogger(Main.class.getName());


    // start point
    public static void main(String[] args) {
        log.info("# ====================== START ======================");

        Users users = new Users();



        /*
        // test db connection
        Scanner scanner = new Scanner(System.in);

//        do {
//            System.out.println("-------------------------------");
//            System.out.print(">> INPUT QUERY: ");
//            String inputString = scanner.nextLine();





            ResultSet rs = DB.getData(inputString);
            try {
                ResultSetMetaData meta = null;
                meta = rs.getMetaData();

                //get column names
                int colCount = meta.getColumnCount();
                ArrayList<String> cols = new ArrayList<String>();
                for (int index=1; index<=colCount; index++)
                    cols.add(meta.getColumnName(index));

                //fetch out rows
                ArrayList<HashMap<String,Object>> rows =
                        new ArrayList<HashMap<String,Object>>();

                while (rs.next()) {
                    HashMap<String,Object> row = new HashMap<String,Object>();
                    for (String colName:cols) {
                        Object val = rs.getObject(colName);
                        row.put(colName,val);
                    }
                    rows.add(row);
                }
                System.out.println("<< " + rows.toString());
            } catch (Exception e) {
                log.error(e.getMessage());
            }

//        } while(true);

        /*

        ServerStarter serverStarter = new ServerStarter();
        if (true)
            new Thread(serverStarter).start();                                                                          // Запуск Jetty Server в новом потоке
        else
            serverStarter.run();                                                                                        // Запуск Jetty Server

         */

    }
}