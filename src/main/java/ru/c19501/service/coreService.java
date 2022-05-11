package ru.c19501.service;

import ru.c19501.service.model.FileRecordDTO;
import ru.c19501.service.model.FileRecordReturnDTO;

import java.util.List;

public interface CoreService {

    boolean createFile(String name, String type, int length);

    FileRecordReturnDTO foundFile(String name, String type);

    List<FileRecordDTO> readFiles();

    boolean deleteFile(String name, String type);

}