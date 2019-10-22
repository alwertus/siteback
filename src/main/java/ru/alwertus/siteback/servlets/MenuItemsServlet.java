package ru.alwertus.siteback.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.alwertus.siteback.db.Pages;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MenuItemsServlet extends HttpServlet implements IServlet  {

    private static Logger log = LogManager.getLogger(MenuItemsServlet.class.getName());
    @Override public String getURL() { return "/menuitems"; }

    // get POST message
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        log.trace("take POST");

        // REQUEST ----------------------------------------
        JSONObject jsonRq = getJsonRequest(request);
        String sessionString = jsonRq.getString("sessionString");
        log.trace(jsonRq.toString());

        // RESPONSE ---------------------------------------
        response.setContentType("application/json;charset=utf-8");
        JSONObject jsonRs = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = Pages.getPageList(sessionString);
        } catch (Exception e) {

        }
        jsonRs.put("errorCode", "0");
        jsonRs.put("errorMsg", "");
        jsonRs.put("items", jsonArray);

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonRs.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}