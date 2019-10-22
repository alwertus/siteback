package ru.alwertus.siteback.db;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Pages {
    private static final Logger log = LogManager.getLogger(Pages.class);

    /**
     * Если пользователь не зареган - выдать страницы, доступные любому (any)
     * иначе смотреть роль пользователя
     * если роль = :
     *      user => страницы = user + any (access_id >= user_role_id)
     *      owner => хозяин + user + any (owner_id=user_id + access_id >= user_role_id)
     *      admin => (access_id >= user_role_id)
     *      god => все страницы
     *
     *
     * @param sessionKey
     * @return
     */
    public static JSONArray getPageList(String sessionKey) {
        log.debug("# get page list for sessionkey=" + sessionKey);
        String userId = Users.getUserId(sessionKey);
        ResultSet rs = null;
        String outFieldList = "*";
        JSONArray jsonArr = new JSONArray();
        try {
            if (userId.equals("") || sessionKey.equals(""))
                rs = DB.getData("select " + outFieldList + " from pagelist where access_id in (select row_id from roles where name='any');");
            else {
                String userRoleName = Users.getRole(userId);
                String userRoleId = DB.getFieldValue("select row_id from roles where name='" + userRoleName + "';", "row_id");
                log.trace("UserRoleName = " + userRoleName);
                log.trace("UserRoleId   = " + userRoleId);
                switch (userRoleName) {
                    case "god":
                        rs = DB.getData("select " + outFieldList + " from pagelist;");
                        break;
                    case "owner":
                        rs = DB.getData("select " + outFieldList + " from pagelist where owner_id='" + userId + "' or access_id>'" + userRoleId + "';");
                        break;
                    case "user":
                    case "admin":
                        rs = DB.getData("select " + outFieldList + " from pagelist where access_id>='" + userRoleId + "';");
                        break;
                    default:
                        rs = DB.getData("select " + outFieldList + " from pagelist where access_id in (select row_id from roles where name='any');");
                }
            }
            while (rs.next()) {
                jsonArr.put(new JSONObject()
                        .put("id", rs.getString("row_id"))
                        .put("name", rs.getString("title"))
                        .put("link", rs.getString("link"))
                );
            }
        } catch (Exception e) {
            log.error("Error get PageList: " + e.getMessage());
        } finally {
            return jsonArr;
        }
        /*
        try {

        } catch (SQLException e) {
            log.error("Error get ResultSet value(s): " + e.getMessage());
        }

        return jsonArr;*/
    }
}
