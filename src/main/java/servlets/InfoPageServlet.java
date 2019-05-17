package servlets;

import db.TableInfoHtml;
import db.TableInfoHtmlList;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class InfoPageServlet extends HttpServlet implements IServlet {
    private TableInfoHtml dbHtml = new TableInfoHtml();
    private TableInfoHtmlList dbHtmlList = new TableInfoHtmlList();

    public InfoPageServlet() {


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

            String linkName = request.getParameter("element") == null ? "" : request.getParameter("element");
            String responseString = "";

            if (linkName.equals("GET_MENU_ITEMS"))
                responseString = dbHtmlList.getAllRecords();
             else
                responseString = dbHtml.getHTML(linkName);

            jsonEnt.put("backgroundColor","#99CC66");
            jsonEnt.put("serverInfo", responseString);

            out.print(jsonEnt.toString());
        }
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
