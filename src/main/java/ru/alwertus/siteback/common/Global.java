package ru.alwertus.siteback.common;

import org.eclipse.jetty.server.Server;

public class Global {
    public static IniFile Config = new IniFile("config.ini", "Config");
    public static IniFile Lang = new IniFile("ru.ini", "Lang");
    public static Server server;
}
