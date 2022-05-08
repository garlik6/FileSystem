package ru.c19501.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.c19501.core.system.FileSystemFactoryImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExampleProgram {
    static ObjectMapper objectMapper = new ObjectMapper();
    public static void main(String[] args) throws JsonProcessingException {
        FileSystemFactory factory = new FileSystemFactoryImpl();
        FileSystem fileSystem = factory.getSystem();
        String fileId = fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a3", "txt", 1, 0);
        fileSystem.addFileInSegment("a4", "txt", 1, 0);
        fileSystem.addFileInSegment("a5", "txt", 1, 0);
        fileSystem.addFileInSegment("a6", "txt", 1, 0);
        fileSystem.addFileInSegment("a7", "txt", 1, 0);
        fileSystem.addFileInSegment("a8", "txt", 1, 0);
        fileSystem.addFileInSegment("a9", "txt", 1, 0);
        fileSystem.addFileInSegment("a10","txt", 11, 0);
//        fileSystem.addFileInSegment("ADDITIONAL","txt", 3, 0);
        List<FileRecord> foundFiles = Arrays.asList(objectMapper.readValue(fileSystem.findFilesInSegmentByName(0, "a1"), FileRecord[].class));
        FileRecord foundFile = objectMapper.readValue(fileSystem.findFileInSegmentById(0,fileId),FileRecord.class);

        System.out.println(foundFiles);
        System.out.println(foundFile.toString());
    }
}
