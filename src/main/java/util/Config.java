package util;



import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by admin on 22.06.2016.
 */
public class Config {

    private static Config instance;
    private static Properties property;

    public static synchronized Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public static synchronized Config getInstance(String path) {
        if (instance == null) {
            instance = new Config(path);
        }
        return instance;
    }

    private Config() {
        this("src\\test\\resources\\config\\application.properties");
    }

    private Config(String path) {
        FileInputStream fis;
        property = new Properties();
        try {
            fis = new FileInputStream(path);
            property.load(fis);
        } catch (Exception e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }

    public static String get(String key) {
        return property.getProperty(key);
    }
}
