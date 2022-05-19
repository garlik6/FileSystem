package ru.c19501.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.c19501.system.FileSystem;
import ru.c19501.system.FileSystemFactory;
import ru.c19501.system.FileSystemFactoryImpl;
import ru.c19501.model.FileRecord.FileRecordDTO;

public class ExampleLoading {
    static ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void main(String[] args) {
    }
}
