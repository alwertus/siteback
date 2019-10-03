package ru.alwertus.siteback.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import ru.alwertus.siteback.common.Global;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Администрирование сайта
 */
public class AdminServlet extends HttpServlet implements IServlet {
    private static Logger log = LogManager.getLogger(AdminServlet.class.getName());

    public AdminServlet() {
        log.debug("constructor");
    }

    @Override public String getURL() { return "/admin"; }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("shutdown");

        if (password.equals("1")) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("Server is shut down from: " + request.getParameter("shutdown") + " sec");
            log.trace("Server is shuting down now");
            System.exit(0);
            /*try {
                Global.server.stop();
            } catch (Exception e) {
                log.error("Error stop server: " + e.getMessage());
            } finally {
                System.exit(0);
            }*/
        }
    }
}