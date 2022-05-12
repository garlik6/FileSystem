package ru.c19501.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.c19501.exceptions.CoreException;
import ru.c19501.core.system.FileSystemFactoryImpl;
import ru.c19501.service.model.FileRecordDTO;

import java.util.Arrays;
import java.util.List;

import static ru.c19501.core.ExampleLoading.objectMapper;

public class ExampleCreatingUpdatingSaving {
    public static void main(String[] args) throws CoreException, JsonProcessingException {
        FileSystemFactory factory = new FileSystemFactoryImpl();
        FileSystem fileSystem = factory.getSystem();
        String fileId = fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        String id1  = fileSystem.addFileInSegment("a3", "txt", 1, 0);
        fileSystem.deleteFileFromSegmentById(0,id1);
        fileSystem.addFileInSegment("a4", "txt", 1, 0);
        fileSystem.addFileInSegment("a5", "txt", 1, 0);

        fileSystem.addFileInSegment("a6", "txt", 1, 0);
        fileSystem.addFileInSegment("a7", "txt", 1, 0);
        String id2 =  fileSystem.addFileInSegment("a8", "txt", 2, 0);
        fileSystem.deleteFileFromSegmentById(0,id2);
        fileSystem.addFileInSegment("a9", "txt", 1, 0);
        fileSystem.addFileInSegment("a10", "txt", 1, 0);
        fileSystem.addFileInSegment("a10", "txt", 10, 0);
        List<FileRecordDTO> foundFiles = Arrays.asList(objectMapper.readValue(fileSystem.findFilesInSegmentByName(0, "a1"), FileRecordDTO[].class));
        FileRecordDTO foundFile = objectMapper.readValue(fileSystem.findFileInSegmentById(0, fileId), FileRecordDTO.class);

        System.out.println(foundFiles);
        System.out.println(foundFile.toString());
        fileSystem.save();
    }
}
