package old.servlets;

import javax.servlet.http.HttpServlet;

public class MainPageServlet extends HttpServlet implements IServlet {
    @Override
    public String getURL() {
        return "/index";
    }
}
