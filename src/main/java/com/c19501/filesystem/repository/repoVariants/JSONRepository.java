package com.c19501.filesystem.repository.repoVariants;

import com.c19501.filesystem.FileSystem.configLoader.ConfigLoader;
import com.c19501.filesystem.repository.Repository;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class JSONRepository extends Repository {
    public JSONRepository() {
        super();
        Properties prop = ConfigLoader.load(new File("src/main/resources/config.properties"));
        this.systemFileName = prop.getProperty("fs.systemJSONFileName");
        this.systemRepository = prop.getProperty("fs.systemJSONRepository");
    }
    @Override
    public void writeRepository() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File JsonFile = new File(systemRepository + "/" + systemFileName);
            objectMapper.writeValue( new FileOutputStream(JsonFile), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Repository loadRepository() {
        return null;
    }
}
