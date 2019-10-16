package ru.alwertus.siteback.db;

import java.util.HashMap;

public class Users {
    public HashMap<String,String> SQL = new HashMap<>();

    // constructor
    public Users() {
        SQL.put("users_createtable",
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
        SQL.put("roles_createtable",
                "CREATE TABLE IF NOT EXISTS ROLES ( " + String.join(", ", new String[]{
                        "row_id INT NOT NULL AUTO_INCREMENT",
                        "name VARCHAR(30)",
                        "PRIMARY KEY(row_id)"
                }) +");");
        SQL.put("users-roles-int_createtable",
                "CREATE TABLE IF NOT EXISTS USR_ROLE_INT ( " + String.join(", ", new String[]{
                        "row_id INT NOT NULL AUTO_INCREMENT",
                        "user_id INT",
                        "role_id INT",
                        "PRIMARY KEY(row_id)"
                }) +");");

        init();
    }

    public void init() {
        DB.executeSQL(SQL.get("users_createtable"));
        DB.executeSQL(SQL.get("roles_createtable"));
        DB.executeSQL(SQL.get("users-roles-int_createtable"));
    }

}
