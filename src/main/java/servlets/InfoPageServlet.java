package servlets;

import db.TableInfoHtml;
import db.TableInfoHtmlList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class InfoPageServlet extends HttpServlet implements IServlet {
    private static final Logger log = LogManager.getLogger(InfoPageServlet.class);
    private TableInfoHtml dbHtml;
    private TableInfoHtmlList dbHtmlList;

    public InfoPageServlet() {
        log.trace("Constructor");
        dbHtml = new TableInfoHtml();
        dbHtmlList = new TableInfoHtmlList();
    }

    @Override
    public String getURL() {
        return "/InfoPageServlet";
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
        response.setContentType("application/json");        // Отправляем от сервера данные в JSON -формате
        response.setCharacterEncoding("utf-8");             // Кодировка отправляемых данных
        try (PrintWriter out = response.getWriter()) {
            JSONObject jsonEnt = new JSONObject();
            String operation = getStringParameter(request, "operation");
            String linkName = getStringParameter(request, "element");
            String responseString = "";

            switch (operation) {
                case "get_menu_items" :
                    ResultSet rs = dbHtmlList.getAllRecords();
                    responseString = "recordcount:" + putResultsetToJSON(rs, jsonEnt);
                    log.trace("Send to front: " + responseString);
                    break;
                case "append" :
                    String title = getStringParameter(request, "title");
                    String page = getStringParameter(request, "page");
                    Boolean result = dbHtmlList.addRecord(title, title, 0, "", false) &&
                                     dbHtml.addRecord(title, new Date(), page);
                    responseString = result ? "OK" : "Error";
                    break;
                case "get_html" :
                    responseString = dbHtml.getHTML(linkName);
                    break;
                default :
                    responseString = "Unknown operation";
                    break;
            }
            jsonEnt.put("serverInfo", responseString);

            out.print(jsonEnt.toString());
        }
    }

    // записываем ResultSet из базы в JSON
    private int putResultsetToJSON(ResultSet rs, JSONObject json) {
        Integer count = 0;
        try {
            while (rs.next()) {
                json.put("title"        + count.toString(), rs.getString("title"));
                json.put("link_text"    + count.toString(), rs.getString("link_text"));
                json.put("position"     + count.toString(), Integer.toString(rs.getInt("position")));
                json.put("parent"       + count.toString(), rs.getString("parent"));
                json.put("category_flag"+ count.toString(), Integer.toString(rs.getInt("category_flag")));
                count++;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return count;
    }

    String getStringParameter(HttpServletRequest request, String paramName) {
        return request.getParameter(paramName) == null ? "" : request.getParameter(paramName);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
        }
    }
}
