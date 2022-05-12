package ru.c19501.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.c19501.core.system.FileSystemFactoryImpl;
import ru.c19501.service.model.FileRecordDTO;

public class ExampleLoading {
    static ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void main(String[] args) throws JsonProcessingException {
        FileSystemFactory factory = new FileSystemFactoryImpl();
        FileSystem fileSystem = factory.getSystem();
        if (!fileSystem.load())
            System.out.println("��� ����������� �������");
        else System.out.println("���� ����������� �������");
        System.out.println(objectMapper.writeValueAsString(objectMapper.readValue(fileSystem.retrieveAllFilesFromSegment(0), FileRecordDTO[].class)));
    }
}
