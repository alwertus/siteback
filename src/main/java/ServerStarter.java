import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;

import javax.servlet.Servlet;

public class ServerStarter implements Runnable {
    private static final String HTML_DIR = "html";              // папка с html файлами
    private static Integer port = 8080;                         // порт сервера по умолчанию
    private static IServlet[] servletlist = {                   // список Сервлетов
            new AdminServlet(),
            new MainPageServlet(),
            new AuthServlet(),
            new InfoPageServlet()
    };

    // конструктор
    public ServerStarter(String[] args) {                       // запуск сервера на нужном порту
        if (args.length == 1)
            port = Integer.valueOf(args[0]);
        System.out.println("Starting server at port: " + port);
    }

    // старт
    @Override
    public void run() {
        System.out.println("Server thread run");

        // ------------------------- запуск веб сервера ----------------------------
        // Создание отдельных сервлетов для разных ссылок
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        for (IServlet servlet : servletlist) {                      // добавляем страницы (кроме главной)
            context.addServlet(new ServletHolder(servlet), servlet.getURL());
        }

        ResourceHandler resourceHandler = new ResourceHandler();    // добавляем страницу по умолчанию
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(HTML_DIR);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {resourceHandler, context});

        Server server = new Server(port);   // новый Jetty сервер
        server.setHandler(handlers);        // ранее сюда передавал context

        // попытка запуска сервера
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        System.out.println("Server thread stopped...");
        // СДЕЛАТЬ ПРАВИЛЬНОЕ ЗАВЕРШЕНИЕ ПОТОКА
    }
}