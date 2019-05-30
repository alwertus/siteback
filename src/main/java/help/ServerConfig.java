package help;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

//TODO сделать самосоздающийся конфиг в папке пользователя

//FIXME многострочный комментарий
// фыв
// фывпп

public class ServerConfig {
    private static final Logger log = LogManager.getLogger(ServerConfig.class);
    private static final String configFileName = "config.ini";
    public static String BD_DATE_FORMAT_STRING = "%Y.%m.%d %H:%i:%S";
    public static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

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