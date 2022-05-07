package com.c19501.repository.repositories;


import com.c19501.config.ConfigLoader;
import com.c19501.repository.Repository;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.util.ArrayList;
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
            File JsonFile = new File(systemRepository + "/" + systemFileName);
            objectMapper.writeValue( new FileOutputStream(JsonFile), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {
        try {
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue( System.out, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
