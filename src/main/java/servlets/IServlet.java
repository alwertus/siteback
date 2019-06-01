package servlets;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;

public interface IServlet extends Servlet {
    String getURL();

    default String getStringParameter(HttpServletRequest request, String paramName) {
        return request.getParameter(paramName) == null ? "" : request.getParameter(paramName);
    }
}
