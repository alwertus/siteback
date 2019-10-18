package ru.alwertus.siteback.db;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;

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
    public static String getPageList(String sessionKey) {
        log.debug("# get page list for sessionkey=" + sessionKey);
        String userId = Users.getUserId(sessionKey);
        ResultSet rs = null;
        String outFieldList = "*";
        if (userId.equals(""))
            rs = DB.getData("select " + outFieldList + " from pagelist where access_id in (select row_id from roles where name='any');");
        else {
            String userRoleName = Users.getRole(userId);
            String userRoleId = DB.getFieldValue("select row_id from roles where name='" + userRoleName + "';", "row_id");
            log.trace("UserRoleName = " + userRoleName);
            log.trace("UserRoleId   = " + userRoleId);
            switch (userRoleName) {
                case "god":
                    rs = DB.getData("select " + outFieldList + " from pagelist;"); break;
                case "owner":
                    rs = DB.getData("select " + outFieldList + " from pagelist where owner_id='" + userId + "' or access_id>'" + userRoleId + "';"); break;
                case "user":
                case "admin":
                    rs = DB.getData("select " + outFieldList + " from pagelist where access_id>='" + userRoleId + "';"); break;
                default:
                    rs = DB.getData("select " + outFieldList + " from pagelist where access_id in (select row_id from roles where name='any');");
            }
        }
        return DB.rsToString(rs);
    }
}
