package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

    private static Properties properties = new Properties();
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not load configuration file: " + CONFIG_FILE_PATH, e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
