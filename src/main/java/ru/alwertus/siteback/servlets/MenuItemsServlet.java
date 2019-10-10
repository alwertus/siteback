package ru.alwertus.siteback.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MenuItemsServlet extends HttpServlet implements IServlet  {

    private static Logger log = LogManager.getLogger(MenuItemsServlet.class.getName());
    @Override public String getURL() { return "/menuitems"; }

    // get POST message
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
//        JSONObject json = new JSONObject();
        String opt = request.getParameter("option");
        log.trace(opt);
    }
}