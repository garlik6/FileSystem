package ru.c19501.core.config;
import java.io.*;
import java.util.Properties;

public class ConfigLoader {
    public static Properties load(File configFile){
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(configFile)){
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    private ConfigLoader(){}
}
