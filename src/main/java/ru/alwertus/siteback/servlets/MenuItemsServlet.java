package ru.alwertus.siteback.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.ServletException;
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
        JSONObject json = new JSONObject();
        log.trace("take POST");
//        String opt = request.getParameter("option");
        String opt = request.getParameter("operation");
        log.trace("read parameter (operation): " + opt);

        // form response
        json.put("key1","val1");
        json.put("kokojamba", "tudom-sudom");
        try (PrintWriter out = response.getWriter()) {
            out.print(json.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}