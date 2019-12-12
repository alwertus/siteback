package ru.alwertus.siteback.servlets;

import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class InfoServlet extends HttpServlet implements IServlet {
    @Override public String getURL() { return "/info"; }

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
            case "getInfoList":
                jsonRs.put("errorCode", "0");
                jsonRs.put("errorMsg", "");
                jsonRs.put("itemList", "{asdasd}");

                try (PrintWriter out = response.getWriter()) {
                    out.print(jsonRs.toString());
                } catch (IOException e) {
                    log.error("Error put message to Response: " + e.getMessage());
                }
                break;
            case "login":
                /*String userName = jsonRq.getString("userLogin");
                String userPass = jsonRq.getString("userPass");
                try (PrintWriter out = response.getWriter()) {
//                    out.print(Users.authUser(userName, userPass).toString());
                } catch (IOException e) {
                    log.error("login error: " + e.getMessage());
                }*/
                break;
        }
    }
}
