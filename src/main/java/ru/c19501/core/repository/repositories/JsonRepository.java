package ru.c19501.core.repository.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.c19501.core.config.ConfigLoader;
import ru.c19501.core.repository.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class JsonRepository extends Repository {

    public JsonRepository() {
        super();
        Properties prop = ConfigLoader.load(new File("src/main/resources/config.properties"));
        this.systemFileName = prop.getProperty("fs.systemJSONFileName");
        this.systemRepository = prop.getProperty("fs.systemJSONRepository");
    }

    @Override
    public void writeRepository() {
        try {
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            File jsonFile = new File(systemRepository + '/' + systemFileName);
            objectMapper.writeValue(new FileOutputStream(jsonFile), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {
        try {
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            String s = objectMapper.writeValueAsString(this);
            System.out.print(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
