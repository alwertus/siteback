package ru.alwertus.siteback.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

abstract class IniFile {
    private static Logger log;
    private static String CONFIG_FILENAME;
    private static Properties prop;                                                                                     // все параметры - тут
    private static String message;
    static {
        log = LogManager.getLogger(IniFile.class);
        CONFIG_FILENAME = "config.ini";
        prop = new Properties();
        loadFromFile();
    }

    // load config file
    private static void loadFromFile() {
        FileInputStream in = null;
        try {
            message = "Load Config from file (" + CONFIG_FILENAME + ") ... ";
            in = new FileInputStream(CONFIG_FILENAME);
            prop.load(in);
            in.close();
            message += "done";
        } catch (Exception e) {
            message += "Error: " + e.getMessage();
        } finally {
            log.debug(message);
        }
    }

    // save config to file
    public static void saveToFile() {
        FileOutputStream out = null;
        try {
            message = "Save Config ... ";
            out = new FileOutputStream(CONFIG_FILENAME);
            prop.store(out, null);
            out.close();
            message += "done";
        } catch (Exception e) {
            message += "Error: " + e.getMessage();
        } finally {
            log.debug(message);
        }
    }

    // return Property from file. If null -> set default value
    public static String getProp(String propertyName, String defaultValue) {
        if (prop.getProperty(propertyName) == null) {
            prop.setProperty(propertyName, defaultValue);
            saveToFile();
        }
        return prop.getProperty(propertyName);
    }

    public static String getProp(String propertyName) {
        return getProp(propertyName, "<CHANGE_ME>");
    }
}
