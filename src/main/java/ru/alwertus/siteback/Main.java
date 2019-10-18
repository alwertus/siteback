package ru.alwertus.siteback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.alwertus.siteback.db.DB;
import ru.alwertus.siteback.db.Pages;
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
//        DB.executeSQL("drop table pagelist, roles, users");
//        DB.tablestructureInit();

        // test db connection
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("-------------------------------");
            System.out.print(">> INPUT COMMAND: ");
            String inputString = scanner.nextLine();

            try {
                String c = inputString.split(":")[0];
                String s1 = inputString.split(":")[1];
                if (c.equals("+")) {
                    String s2 = inputString.split(":")[2];
                    log.trace(Users.authUser(s1, s2));
                }
                if (c.equals("-")) {
                    log.trace(Users.logoutUser(s1));
                }
                if (c.equals("g")) {
                    log.trace(Pages.getPageList(s1));
                }
            } catch (Exception e) {
                log.error("Error: " + e.getMessage());
            }
//            System.out.println(DB.rsToString(DB.getData(inputString)));
        } while(true);

/*
        ServerStarter serverStarter = new ServerStarter();
        if (true)
            new Thread(serverStarter).start();                                                                          // Запуск Jetty Server в новом потоке
        else
            serverStarter.run();                                                                                        // Запуск Jetty Server

         */

    }
}