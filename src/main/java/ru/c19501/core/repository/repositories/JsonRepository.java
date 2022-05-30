package ru.c19501.core.repository.repositories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.c19501.config.ConfigLoader;
import ru.c19501.core.files.*;
import ru.c19501.core.files.JsonRelated.MixInFR;
import ru.c19501.core.files.JsonRelated.MixInR;
import ru.c19501.core.files.JsonRelated.Views;
import ru.c19501.core.repository.Repository;
import ru.c19501.defragmentation.Defragmentation;
import ru.c19501.defragmentation.DefragmentationFunctions;
import ru.c19501.exceptions.CoreException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


public class JsonRepository extends Repository {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public JsonRepository() {
        super();
        Properties prop = ConfigLoader.properties;
        this.systemFileName = prop.getProperty("fs.systemJSONFileName");
        this.systemRepository = prop.getProperty("fs.systemJSONRepository");
    }

    public JsonRepository(String name) {
        super();
        Properties prop = ConfigLoader.properties;
        this.systemFileName = name;
        this.systemRepository = prop.getProperty("fs.systemJSONRepository");
    }

    public JsonRepository(int space, int freeSpace, int readyToAddSpace, List<Segment> segments) {
        super(space,freeSpace,readyToAddSpace,segments);
        Properties prop = ConfigLoader.properties;
        this.systemFileName = prop.getProperty("fs.systemJSONFileName");
        this.systemRepository = prop.getProperty("fs.systemJSONRepository");
    }

    public JsonRepository(Repository repository) {
        super(repository);
        Properties prop = ConfigLoader.properties;
        this.systemFileName = prop.getProperty("fs.systemJSONFileName");
        this.systemRepository = prop.getProperty("fs.systemJSONRepository");
    }

    @Override
    public void writeRepository() {
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            File jsonFile = new File(systemRepository + '/' + systemFileName);
            objectMapper.writerWithView(Views.Internal.class).writeValue(new FileOutputStream(jsonFile), this);
            objectMapper.disable(SerializationFeature.INDENT_OUTPUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @JsonIgnore
    @Override
    public String getCurrentJson() throws JsonProcessingException {
        try {
            return objectMapper.addMixIn(FileRecord.class, MixInFR.class).addMixIn(Repository.class, MixInR.class).writerWithView(Views.Internal.class).writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        objectMapper = new ObjectMapper();
        return "";
    }
    @JsonIgnore
    public String getPublicJson(){
        try {
            return objectMapper.writerWithView(Views.Public.class).writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        objectMapper = new ObjectMapper();
        return "";
    }

    @Override
    public String fileRecordsToString(FileRecord fileRecord) {
        try {
            return objectMapper.writerWithView(Views.Public.class).writeValueAsString(fileRecord);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String fileRecordsToString(List<FileRecord> fileRecords) {
        try {
            return objectMapper.writerWithView(Views.Public.class).writeValueAsString(fileRecords);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void print() {
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String s = objectMapper.writerWithView(Views.Public.class).writeValueAsString(this);
            //TODO add logging to spot bugs
            System.out.print(s);
            objectMapper.disable(SerializationFeature.INDENT_OUTPUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void defragmentation() throws CoreException {
        Defragmentation.defragment(this);
    }

    @Override
    public String defragExt() {
        return Double.toString(DefragmentationFunctions.defragExt(this));
    }
}
