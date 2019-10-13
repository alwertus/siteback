package ru.alwertus.siteback.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MenuItemsServlet extends HttpServlet implements IServlet  {

    private static Logger log = LogManager.getLogger(MenuItemsServlet.class.getName());
    @Override public String getURL() { return "/menuitems"; }

    // get POST message
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        log.trace("take POST");

        // REQUEST ----------------------------------------
        JSONObject jsonRq = getJsonRequest(request);
        log.info(jsonRq.toString());

        log.info("operation=" + jsonRq.getString("operation"));
        log.info("userLoginAs=" + jsonRq.getString("userLoginAs"));
        String sessionString = jsonRq.getString("sessionString");

        // RESPONSE ---------------------------------------
        response.setContentType("application/json;charset=utf-8");
        JSONObject jsonRs = new JSONObject();
        JSONArray jsonArr = new JSONArray();

        //'{"name":"Главная","link":"link1","id":"1"},' +
//                '{"name":"Володя","link":"link2","id":"2"},' +
//                '{"name":"Мащьпулькэ","link":"link3","id":"3"},' +
//                '{"name":"Гуманойд","link":"link4","id":"4"}' +

        JSONObject jsonItem1 = new JSONObject();
        jsonItem1.put("id", "1");
        jsonItem1.put("name", "Главная");
        jsonItem1.put("link", "link1");
        jsonArr.put(jsonItem1);

        if (sessionString.equals("1111111111")) {
            JSONObject jsonItem2 = new JSONObject();
            jsonItem2.put("id", "2");
            jsonItem2.put("name", "Володя");
            jsonItem2.put("link", "link2");
            jsonArr.put(jsonItem2);
        }

        if (sessionString.equals("2222222222")) {
            JSONObject jsonItem3 = new JSONObject();
            jsonItem3.put("id", "3");
            jsonItem3.put("name", "Мащьпулькэ");
            jsonItem3.put("link", "link3");
            jsonArr.put(jsonItem3);
        }

        if (sessionString.equals("1111111111") ||
                sessionString.equals("2222222222") ||
                sessionString.equals("3333333333")) {
            JSONObject jsonItem4 = new JSONObject();
            jsonItem4.put("id", "4");
            jsonItem4.put("name", "Гуманойд");
            jsonItem4.put("link", "link4");
            jsonArr.put(jsonItem4);
        }

        jsonRs.put("errorCode", "0");
        jsonRs.put("errorMsg", "");
        jsonRs.put("count", "4");
        jsonRs.put("items", jsonArr);

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonRs.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}