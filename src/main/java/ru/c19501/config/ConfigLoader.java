package ru.c19501.config;
import java.io.*;
import java.util.Properties;

public class ConfigLoader {

    static public final Properties properties;
    static {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")){
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        properties = prop;
    }

    private ConfigLoader(){}
}
