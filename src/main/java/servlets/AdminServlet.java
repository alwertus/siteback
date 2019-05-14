package servlets;

import javax.servlet.http.HttpServlet;

public class AdminServlet extends HttpServlet implements IServlet {

    @Override
    public String getURL() {
        return "/admin";
    }
}
