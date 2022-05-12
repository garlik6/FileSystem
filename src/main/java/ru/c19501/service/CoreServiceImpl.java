package ru.c19501.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.c19501.core.FileSystem;
import ru.c19501.core.config.ConfigLoader;
import ru.c19501.exceptions.CoreException;
import ru.c19501.model.FileRecord.FileRecordDTO;
import ru.c19501.model.FileRecord.FileRecordReturnDTO;
import ru.c19501.service.mapper.FileRecordMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoreServiceImpl implements CoreService {

    private final int countSegments;
    private final FileSystem fileSystem;
    private final ObjectMapper objectMapper;

    public CoreServiceImpl(FileSystem fileSystem, ObjectMapper objectMapper) {
        this.countSegments = Integer.parseInt(ConfigLoader.properties.getProperty("fs.segmentAmountInCatalog"));
        this.fileSystem = fileSystem;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean createFile(String name, String type, int length) {

        if (foundFile(name, type) == null) {
            try {
                for (int i = 0; i < countSegments; i++) {
                    if (fileSystem.getFreeSpaceInSegment(i) >= length) {
                        fileSystem.addFileInSegment(name, type, length, i);
                        return true;
                    }
                }
            } catch (CoreException e) {
                System.err.println(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public FileRecordReturnDTO foundFile(String name, String type) {

        if(isCorrectName(name)){
            System.err.println("Not correct name");
            return null;
        }

        FileRecordDTO file;
        for (int i = 0; i < countSegments; i++) {
            file = foundFileByNameAndType(name, type, i);
            if (file != null && !file.isDeleted()) {
                return FileRecordMapper.dtoToReturnDto(file);
            }
        }
        return null;
    }

    @Override
    public List<FileRecordReturnDTO> readFiles() {
        List<FileRecordDTO> fileRecordDTOList = new ArrayList<>();
        try {
            for (int i = 0; i < countSegments; i++) {
                FileRecordDTO[] fileRecordDTOs = getFilesBySegment(i);
                for (FileRecordDTO file : fileRecordDTOs) {
                    if (!file.isDeleted()) {
                        fileRecordDTOList.add(file);
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
        return fileRecordDTOList.stream()
                .map(FileRecordMapper::dtoToReturnDto)
                .toList();
    }

    @Override
    public boolean deleteFile(String name, String type) {
        if(isCorrectName(name)){
            System.err.println("Not correct name");
            return false;
        }

        try {
            FileRecordDTO file;
            for (int i = 0; i < countSegments; i++) {
                file = foundFileByNameAndType(name, type, i);
                if (file != null) {
                    fileSystem.deleteFileFromSegmentById(i, file.getId());
                    return true;
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public void defragmentation() {

    }

    private FileRecordDTO foundFileByNameAndType(String name, String type, int segmentId) {
        List<FileRecordDTO> fileRecordDTOList = new ArrayList<>();
        try {
            FileRecordDTO[] fileRecordDTOs = getFilesBySegmentAndName(segmentId, name);
            fileRecordDTOList.addAll(List.of(fileRecordDTOs));

            for (FileRecordDTO file : fileRecordDTOList) {
                if (file.getFileType().equals(type)) {
                    return file;
                }
            }
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private FileRecordDTO[] getFilesBySegmentAndName(int segmentId, String name) throws JsonProcessingException {
        return objectMapper.readValue(fileSystem.findFilesInSegmentByName(segmentId, name), FileRecordDTO[].class);
    }

    private FileRecordDTO[] getFilesBySegment(int segmentId) throws JsonProcessingException {
        return objectMapper.readValue(fileSystem.retrieveAllFilesFromSegment(segmentId), FileRecordDTO[].class);
    }

    private boolean isCorrectName(String name){
        Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(name);
        return  matcher.find();
    }

}