package ru.c19501.service;

import ru.c19501.model.FileRecord.FileRecordReturnDTO;

import java.util.List;

/**
 * @author команда сервиса
 * @version 1.0
 */
public interface CoreService {

    /**
     * @param name имя файла
     * @param type расширение файла (без точки)
     * @param length длинна файла
     * @return true, если файл был создан, иначе false
     */
    boolean createFile(String name, String type, int length);

    /**
     * @param name имя файла
     * @param type расширение файла (без точки)
     * @return найденный файл, либо null
     */
    FileRecordReturnDTO foundFile(String name, String type);

    /**
     * @return возвращает основную информацию о всех файлах в системе, если файлов нет, то вернется ПУСТОЙ лист
     */
    List<FileRecordReturnDTO> readFiles();

    /**
     * @param name имя файла
     * @param type расширение файла (без точки)
     * @return true, если файл был удален, иначе false
     */
    boolean deleteFile(String name, String type);

    /**
     * @author команда дефрагментации
     */
    void defragmentation();
}