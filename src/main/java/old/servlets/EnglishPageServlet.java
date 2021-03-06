package old.servlets;

import old.db.TableEnglish;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EnglishPageServlet extends HttpServlet implements IServlet {
    private static final Logger log = LogManager.getLogger(EnglishPageServlet.class);
    TableEnglish dbEng;

    @Override public String getURL() { return "/EnglishPageServlet"; }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (JSONException e) {
            log.error("DoPost Error: " + e.getMessage());
        }
    }

    // constructor
    public EnglishPageServlet() {
        dbEng = new TableEnglish();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");        // Отправляем от сервера данные в JSON -формате
        response.setCharacterEncoding("utf-8");             // Кодировка отправляемых данных
        try (PrintWriter out = response.getWriter()) {
            JSONObject jsonEnt = new JSONObject();
            String operation = getStringParameter(request, "operation");
            String responseString = "";

            switch (operation) {
                case "kkkkk" :
                    responseString = "aaazzzz";
                    break;

                default :
                    responseString = "Unknown operation";
                    break;
            }
            jsonEnt.put("serverInfo", responseString);

            out.print(jsonEnt.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
