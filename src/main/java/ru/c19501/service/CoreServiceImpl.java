package ru.c19501.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.c19501.core.FileSystem;
import ru.c19501.core.config.ConfigLoader;
import ru.c19501.exceptions.CoreException;
import ru.c19501.service.mapper.FileRecordMapper;
import ru.c19501.model.FileRecord.FileRecordDTO;
import ru.c19501.model.FileRecord.FileRecordReturnDTO;

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

    //TODO добавить валидацию
    @Override
    public boolean createFile(String name, String type, int length) {
        if (foundFile(name, type) == null) {
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
        }
        return false;
    }

    //TODO добавить валидацию
    @Override
    public FileRecordReturnDTO foundFile(String name, String type) {
        FileRecordDTO file = foundFileByNameAndType(name, type);
        if (file == null) return null;
        return FileRecordMapper.dtoToReturnDto(file);
    }

    @Override
    public List<FileRecordReturnDTO> readFiles() {
        try {

            List<FileRecordDTO> fileRecordDTOList = new ArrayList<>();
            for (int i = 0; i < countSegments; i++) {
                FileRecordDTO[] fileRecordDTOs = getFilesBySegment(i);
                fileRecordDTOList.addAll(Arrays.asList(fileRecordDTOs));
            }

            return fileRecordDTOList.stream()
                    .map(FileRecordMapper::dtoToReturnDto)
                    .toList();

        } catch (JsonProcessingException e) {
            e.getMessage();
        }

        return new ArrayList<>();
    }

    @Override
    public boolean deleteFile(String name, String type) {
        FileRecordDTO file = foundFileByNameAndType(name, type);
/*        if (file == null) {
            try {
                fileSystem.deleteFileFromSegmentById(file.getId());
            } catch () {

            }
        }*/
        return false;
    }

    @Override
    public void defragmentation() {

    }

    private FileRecordDTO foundFileByNameAndType(String name, String type) {
        try {

            List<FileRecordDTO> fileRecordDTOList = new ArrayList<>();
            for (int i = 0; i < countSegments; i++) {
                FileRecordDTO[] fileRecordDTOs = getFilesBySegmentAndName(i, name);
                fileRecordDTOList.addAll(Arrays.asList(fileRecordDTOs));
            }

            for (FileRecordDTO file : fileRecordDTOList) {
                if (file.getFileType().equals(type)) {
                    return file;
                }
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private FileRecordDTO[] getFilesBySegmentAndName(int segmentId, String name) throws JsonProcessingException {
        return objectMapper.readValue(fileSystem.findFilesInSegmentByName(segmentId, name), FileRecordDTO[].class);
    }

    private FileRecordDTO[] getFilesBySegment(int segmentId) throws JsonProcessingException {
        return objectMapper.readValue(fileSystem.retrieveAllFilesFromSegment(segmentId), FileRecordDTO[].class);
    }

}