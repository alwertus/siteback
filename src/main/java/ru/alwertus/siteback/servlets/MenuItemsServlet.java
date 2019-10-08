package ru.alwertus.siteback.servlets;

import javax.servlet.http.HttpServlet;

public class MenuItemsServlet extends HttpServlet implements IServlet  {
    @Override
    public String getURL() {
        return "/menuitems";
    }
}
