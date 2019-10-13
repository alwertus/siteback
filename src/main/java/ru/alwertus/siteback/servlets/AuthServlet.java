package ru.alwertus.siteback.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

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

        // REQUEST ----------------------------------------
        JSONObject jsonRq = getJsonRequest(request);
        log.trace(jsonRq.toString());

        String operation = jsonRq.getString("operation");

        if (!operation.equals("login")) return;

        String userName = jsonRq.getString("userLogin");
        String userPass = jsonRq.getString("userPass");

        // RESPONSE ---------------------------------------
        JSONObject jsonRs = new JSONObject();
        response.setContentType("application/json;charset=utf-8");

        if (userName.equals("1")&&userPass.equals("1")) {
            jsonRs.put("sessionString","1111111111");
            jsonRs.put("loginName","Володя");
            jsonRs.put("errorCode","0");
            jsonRs.put("errorMsg","");
        } else if (userName.equals("2")&&userPass.equals("2")) {
            jsonRs.put("sessionString","2222222222");
            jsonRs.put("loginName","Машуличка");
            jsonRs.put("errorCode","0");
            jsonRs.put("errorMsg","");
        } else if (userName.equals("3")&&userPass.equals("3")) {
            jsonRs.put("sessionString","3333333333");
            jsonRs.put("loginName","Левыйчел");
            jsonRs.put("errorCode","0");
            jsonRs.put("errorMsg","");
        } else {
            jsonRs.put("sessionString","");
            jsonRs.put("loginName","Гость");
            jsonRs.put("errorCode","1");
            jsonRs.put("errorMsg","Нет такого пользователя");
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonRs.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}