package servlets;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthServlet extends HttpServlet implements IServlet {
    @Override
    public String getURL() {
        return "/AuthServlet";
    }

    /*@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        //login = request.getParameter(PARAMETERNAME_LOGIN);
        response.setContentType(PageGenerator.CONTENT_TYPE);
        response.setStatus((login == null || login.isEmpty()) ?
                HttpServletResponse.SC_FORBIDDEN :
                HttpServletResponse.SC_OK);
        try {
            response.getWriter().println(fillPageParameters());
        } catch (IOException e) {
            System.out.println("ERROR (AuthenticationPageServlet.doPost): " + e.toString());
        }
    }*/


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
        response.setContentType("application/json");//Отправляем от сервера данные в JSON -формате
        response.setCharacterEncoding("utf-8");//Кодировка отправляемых данных
        try (PrintWriter out = response.getWriter()) {
            JSONObject jsonEnt = new JSONObject();

            String linkName = request.getParameter("element") == null ? "" : request.getParameter("element");
            String resultString = "Ты жмакнул на ссылку " + linkName;

            jsonEnt.put("backgroundColor","#99CC66");
            jsonEnt.put("serverInfo", resultString);

            /*if(request.getParameter("login").equals("myLogin")&&request.getParameter("password").equals("myPassword"))
            {
                jsonEnt.put("backgroundColor","#99CC66");
                jsonEnt.put("serverInfo", "Вы вошли!");
            }else
            {
                jsonEnt.put("backgroundColor","#CC6666");
                jsonEnt.put("serverInfo", "Введен неправильный логин или пароль!");
            }*/
            out.print(jsonEnt.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            //Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }//

}
