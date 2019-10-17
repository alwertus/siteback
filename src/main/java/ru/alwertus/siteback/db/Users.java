package ru.alwertus.siteback.db;

import java.util.HashMap;

public class Users {
    public HashMap<String,String> SQL = new HashMap<>();

    // constructor
    public Users() {
        SQL.put("1.0",
                "CREATE TABLE IF NOT EXISTS USERS ( " + String.join(", ", new String[]{
                        "row_id INT NOT NULL AUTO_INCREMENT",
                        "name VARCHAR(30)",
                        "login VARCHAR(30)",
                        "password VARCHAR(30)",
                        "created DATETIME",
                        "lastlogin DATETIME",
                        "sessionkey VARCHAR(20)",
                        "PRIMARY KEY(row_id)"
                }) +");");

        SQL.put("2.0",
                "CREATE TABLE IF NOT EXISTS ROLES ( " + String.join(", ", new String[]{
                        "row_id INT NOT NULL AUTO_INCREMENT",
                        "name VARCHAR(30)",
                        "PRIMARY KEY(row_id)"
                }) +");");
        SQL.put("2.1", "INSERT IGNORE INTO ROLES (row_id, name) VALUES ('1','owner'),('2','admin'),('3','user'),('4','none');");

        SQL.put("3.0",
                "CREATE TABLE IF NOT EXISTS USR_ROLE_INT ( " + String.join(", ", new String[]{
                        "row_id INT NOT NULL AUTO_INCREMENT",
                        "user_id INT",
                        "role_id INT",
                        "PRIMARY KEY(row_id)"
                }) +");");
        SQL.put("3.1", "INSERT IGNORE INTO USR_ROLE_INT (row_id, name) VALUES ('1','owner'),('2','admin'),('3','user'),('4','none');");

        init();
    }

    public void init() {
        DB.executeSQL(SQL.get("1.0"));
        DB.executeSQL(SQL.get("2.0"));
        DB.executeSQL(SQL.get("2.1"));
        DB.executeSQL(SQL.get("3.0"));
        DB.executeSQL(SQL.get("3.1"));

    }

}
