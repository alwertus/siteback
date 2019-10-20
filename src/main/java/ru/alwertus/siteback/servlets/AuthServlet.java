package ru.alwertus.siteback.servlets;

import old.db.DBOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.alwertus.siteback.db.Users;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthServlet extends HttpServlet implements IServlet  {

    private static Logger log = LogManager.getLogger(AuthServlet.class.getName());
    @Override public String getURL() { return "/auth"; }

    // get POST message
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        log.trace("take POST");
        response.setContentType("application/json;charset=utf-8");
        JSONObject jsonRq = getJsonRequest(request);
        JSONObject jsonRs = new JSONObject();
        String operation = jsonRq.getString("operation");

        log.trace(jsonRq.toString());
        switch (operation) {
            case "logout":
                jsonRs.put("errorCode", "0");
                jsonRs.put("errorMsg", Users.logoutUser(jsonRq.getString("sessionString")));
                try (PrintWriter out = response.getWriter()) {
                    out.print(jsonRs.toString());
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                break;
            case "login":
                String userName = jsonRq.getString("userLogin");
                String userPass = jsonRq.getString("userPass");
                try (PrintWriter out = response.getWriter()) {
                    out.print(Users.authUser(userName, userPass).toString());
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                break;
        }
    }
}