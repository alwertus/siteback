package servlets;

import db.TableInfo;
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
    private TableInfo dbInfo;

    public InfoPageServlet() {
        log.trace("Constructor");
        dbInfo = new TableInfo();
    }

    @Override
    public String getURL() {
        return "/InfoPageServlet";
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");        // Отправляем от сервера данные в JSON -формате
        response.setCharacterEncoding("utf-8");             // Кодировка отправляемых данных
        try (PrintWriter out = response.getWriter()) {
            JSONObject jsonEnt = new JSONObject();
            String operation = getStringParameter(request, "operation");
            String linkName = getStringParameter(request, "element");
            String responseString = "";
            String title;
            String page;
            Integer par_id;
            Integer result;

            switch (operation) {
                case "get_branch" :
                    responseString = "recordcount:" +
                            putResultsetToJSON (
                                dbInfo.getBranch(Integer.valueOf(linkName)),
                                jsonEnt,
                                new String[] { "row_id", "title", "child_count" }
                            );
                    log.trace("Send refreshed branch at ID=" + linkName + " " + responseString);
                    break;
                case "append_page":
                    title = getStringParameter(request, "title");
                    page = getStringParameter(request, "page");
                    par_id = Integer.valueOf(getStringParameter(request, "element"));
                    result = dbInfo.addRecord(par_id, 0, new Date(), title, page);
                    responseString = result != -1 ? par_id.toString() : result.toString();
                    break;
                case "edit_page" :
                    title = getStringParameter(request, "title");
                    page = getStringParameter(request, "page");
                    par_id = Integer.valueOf(getStringParameter(request, "element"));
                    Integer record_id = Integer.valueOf(getStringParameter(request, "record_id"));
                    if (par_id == -1)
                        par_id = dbInfo.getParentId(record_id);
                    result = dbInfo.updateRecord(record_id, par_id, title, page);
                    responseString = result != -1 ? par_id.toString() : result.toString();
                    break;
                case "del_page" :
                    //TODO доделать удаление записи
                    break;
                case "get_html" :
                    responseString = dbInfo.getHTML(linkName);
                    break;
                default :
                    responseString = "Unknown operation";
                    break;
            }
            jsonEnt.put("serverInfo", responseString);

            out.print(jsonEnt.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (NumberFormatException e) {
            log.error("Error convert to Integer: " + e.getMessage());
        }
    }

    // записываем ResultSet из базы в JSON
    private int putResultsetToJSON(ResultSet rs, JSONObject json, String[] arrColumns) {
        Integer count = 0;
        try {
            while (rs.next()) {
                for (String column : arrColumns) {
                    json.put(column + count.toString(), rs.getString(column));
                }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (JSONException e) {
            log.error("DoPost Error: " + e.getMessage());
        }
    }
}
