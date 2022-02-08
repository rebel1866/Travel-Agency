package by.epamtc.stanislavmelnikov.controller.controllerutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
    private final static String CONFIG_PATH = "C:\\Users\\Stas\\IdeaProjects\\TravelAgency\\src\\main\\resources\\config.properties";
    //config.properties

    public static String getProperty(String key) throws IOException {
        Properties property = new Properties();
        String values;
        FileInputStream fileInputStream = new FileInputStream(CONFIG_PATH);
        property.load(fileInputStream);
        values = property.getProperty(key);
        return values;
    }
}
