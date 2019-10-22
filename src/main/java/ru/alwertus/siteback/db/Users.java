package ru.alwertus.siteback.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Users {
    private static final Logger log = LogManager.getLogger(Users.class);

    public static JSONObject authUser(String login, String password) {
        log.debug("# Try to authentication user (login:pass) = " + login + ":" + password);
        JSONObject jsonRs = new JSONObject();
        try {
            String userId = getUserId(login, password);
            if (userId.equals("")) {
                jsonRs.put("sessionString", "");
                jsonRs.put("loginName", "Гость");
                jsonRs.put("errorCode", "1");
                jsonRs.put("errorMsg", "Нет такого пользователя");
            } else {
                String sessionKey = generateNewSessionKey(userId);
                jsonRs.put("sessionString", sessionKey);
                jsonRs.put("loginName", getUserName(sessionKey));
                jsonRs.put("errorCode", "0");
                jsonRs.put("errorMsg", "");
            }
        } catch (Exception e) {
            jsonRs.put("sessionString", "");
            jsonRs.put("loginName", "Гость");
            jsonRs.put("errorCode", "2");
            jsonRs.put("errorMsg", e.getMessage());
        } finally {
            return jsonRs;
        }
    }

    // return OK:Logout or ERR:0
    public static String logoutUser(String sessionKey) {
        log.debug("# User logout (sessionkey");
        String userId = getUserId(sessionKey);
        if (userId.equals("")) return "ERR:0";
        DB.executeSQL("update users set sessionkey='' where row_id='" + userId + "';");
        return "OK:Logout";
    }

    private static String getUserId(String login, String password) {
        return DB.getFieldValue("select row_id from users where login='" + login + "' and password='" + password + "';", "row_id");
    }

    public static String getUserId(String sessionKey) {
        return DB.getFieldValue("select row_id from users where sessionkey='" + sessionKey + "';", "row_id");
    }

    public static String getUserName(String sessionKey) {
        return DB.getFieldValue("select name from users where sessionkey='" + sessionKey + "';", "name");
    }

    public static String getRole(String userId) {
        return DB.getFieldValue("select t2.name as role from users t1, roles t2 where t1.role_id = t2.row_id and t1.row_id='" + userId + "';", "role");
    }

    // generate session
    private static String generateNewSessionKey(String userId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssz");
        String newSessionKey = "~" + userId + "~" + dateFormat.format(new Date()) + "~";

        DB.executeSQL("update users set sessionkey='" + newSessionKey + "', lastlogin=NOW() where row_id='" + userId + "';");
        return DB.getFieldValue("select sessionkey from users where row_id='" + userId + "';","sessionkey");
    }
}
