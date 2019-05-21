package help;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class ServerConfig {
    private static final Logger log = LogManager.getLogger(ServerConfig.class);
    private static final String configFileName = "config.ini";

    public static String getProperty(String propertyName) {
        InputStream inputStream;
        Properties prop = new Properties();
        inputStream = ServerConfig.class.getClassLoader().getResourceAsStream(configFileName);
        String result = "";
        try {
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                log.error("Error load cfg file");
            }
            result = prop.getProperty(propertyName);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return result;
    }
}