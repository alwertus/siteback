package ru.alwertus.siteback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.alwertus.siteback.common.Config;
import ru.alwertus.siteback.servlets.IServlet;

public class ServerStarter implements Runnable {
    private static final Logger log = LogManager.getLogger(ServerStarter.class);                                        // логгер
    private static final String HTML_DIR = Config.getProp("html_dir", "html");                                          // папка с FRONT сайтом
    private static Integer port = Integer.parseInt(Config.getProp("server_port", "5188"));                              // порт
    private IServlet[] servletlist = {                                                                                  // список Сервлетов
//            new MainPageServlet()
    };

    @Override
    public void run() {
        log.debug("Thread server is run");

        // ------------------------- запуск веб сервера ----------------------------
        // Создание отдельных сервлетов для разных ссылок
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        for (IServlet servlet : servletlist) {                                                                          // добавляем страницы (кроме главной)
            context.addServlet(new ServletHolder(servlet), servlet.getURL());
        }

        ResourceHandler resourceHandler = new ResourceHandler();                                                        // добавляем страницу по умолчанию
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(HTML_DIR);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {resourceHandler, context});

        Server server = new Server(port);                                                                               // новый Jetty сервер
        server.setHandler(handlers);                                                                                    // ранее сюда передавал context

        try {                                                                                                           // попытка запуска сервера
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Constructor
    public ServerStarter() {
        log.debug("Empty constructor");
    }
}
