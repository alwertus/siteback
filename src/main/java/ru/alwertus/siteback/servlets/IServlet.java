package ru.alwertus.siteback.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

public interface IServlet extends Servlet {
    Logger log = LogManager.getLogger(IServlet.class.getName());
    String getURL();

    // Servlet request -> to JSON
    default JSONObject getJsonRequest(HttpServletRequest rq) {
        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = rq.getReader();
            while ((line = reader.readLine()) != null)
                sb.append(line);

        } catch (Exception e) {
            log.error("Error read request: " + e.getMessage());
        }
        JSONObject resultJson = null;
        try {
            resultJson = new JSONObject(sb.toString());
        } catch (JSONException e) {
            log.error("Error parsing JSON: " + e.getMessage());
        }
        return resultJson;
    }
}
