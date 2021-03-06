package ru.alwertus.siteback.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class IniFile {
    private Logger log;
    private String FILENAME;
    private Properties prop = new Properties();                                                                         // все параметры - тут
    private String message;

    // Constructor
    public IniFile(String fileName, String logName) {
        FILENAME = fileName;
        log = LogManager.getLogger(logName);
        loadFromFile();
        getProp("THIS_FILENAME", FILENAME);
    }

    // =================================================================================================================
    // ----- ----- SETTER, GETTER ----- -----
    public String getFilename() { return FILENAME; }

    // return Property from file. If null -> set default value
    public String getProp(String propertyName, String defaultValue) {
        if (prop.getProperty(propertyName) == null) {
            prop.setProperty(propertyName, defaultValue);
            saveToFile();
        }
        return prop.getProperty(propertyName);
    }

    /*
    public String getProp(String propertyName) {
        return getProp(propertyName, "<CHANGE_ME>");
    }*/

    // =================================================================================================================
    // ----- ----- FUNCTIONS ----- -----

    // load config file
    private void loadFromFile() {
        FileInputStream in;
        try {
            message = "Load Config from file (" + FILENAME + ") ... ";
            in = new FileInputStream(FILENAME);
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
    private void saveToFile() {
        FileOutputStream out;
        try {
            message = "Save Config ... ";
            out = new FileOutputStream(FILENAME);
            prop.store(out, null);
            out.close();
            message += "done";
        } catch (Exception e) {
            message += "Error: " + e.getMessage();
        } finally {
            log.debug(message);
        }
    }
}