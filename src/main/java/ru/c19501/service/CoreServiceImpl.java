package ru.c19501.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.c19501.core.FileSystem;
import ru.c19501.core.config.ConfigLoader;
import ru.c19501.exceptions.CoreException;
import ru.c19501.service.model.FileRecordDTO;
import ru.c19501.service.model.FileRecordReturnDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoreServiceImpl implements CoreService {

    int countSegments;
    FileSystem fileSystem;
    ObjectMapper objectMapper;

    public CoreServiceImpl(FileSystem fileSystem, ObjectMapper objectMapper) {
        this.countSegments = Integer.parseInt(ConfigLoader.properties.getProperty("fs.segmentAmountInCatalog"));
        this.fileSystem = fileSystem;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean createFile(String name, String type, int length) {
        try {
            for (int i = 0; i < countSegments; i++) {
                int freeSpace = fileSystem.getFreeSpaceInSegment(i);
                if (freeSpace >= length) {
                    fileSystem.addFileInSegment(name, type, length, i);
                    return true;
                }
            }
        } catch (CoreException e) {
            System.err.println(e);
        }
        return false;
    }

    @Override
    public FileRecordReturnDTO foundFile(String name, String type) {
        for (int i = 0; i < countSegments; i++) {
            fileSystem.retrieveAllFilesFromSegment(i);
        }
        return null;
    }


    @Override
    public List<FileRecordDTO> readFiles() {
        try {
            List<FileRecordDTO> fileRecordDTOList = new ArrayList<>();
            for (int i = 0; i < countSegments; i++) {
                FileRecordDTO[] fileRecordDTOs = getFilesBySegment(i);
                fileRecordDTOList.addAll(Arrays.asList(fileRecordDTOs));
            }
            return fileRecordDTOList;
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public boolean deleteFile(String name, String type) {
        //fileSystem.findFileInSegmentById(?,id);
        return false;
    }

    private FileRecordDTO[] getFilesBySegment(int segmentId) throws JsonProcessingException {
        return objectMapper.readValue(fileSystem.retrieveAllFilesFromSegment(segmentId), FileRecordDTO[].class);
    }
}