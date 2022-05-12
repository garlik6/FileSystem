package ru.c19501.service;

import ru.c19501.model.FileRecord.FileRecordReturnDTO;

import java.util.List;

/**
 * @author команда сервиса
 * @version 1.0
 */
public interface CoreService {

    /**
     * Метод для создания файла
     *
     * @param name имя файла
     * @param type расширение файла (без точки)
     * @param length длинна файла
     * @return true, если файл был создан, иначе false
     */
    boolean createFile(String name, String type, int length);

    /**
     * Метод для поиска файла, помогает узнать дату создания файла
     *
     * @param name имя файла
     * @param type расширение файла (без точки)
     * @return найденный файл, либо null
     */
    FileRecordReturnDTO foundFile(String name, String type);

    /**
     * Метод для того, чтобы узнать все активные в системе файлы
     *
     * @return возвращает основную информацию о всех файлах в системе, если файлов нет, то вернется ПУСТОЙ лист
     */
    List<FileRecordReturnDTO> readFiles();

    /**
     * Метод для удаления файла
     *
     * @param name имя файла
     * @param type расширение файла (без точки)
     * @return true, если файл был удален, иначе false
     */
    boolean deleteFile(String name, String type);

    /**
     * @author команда дефрагментации
     *
     * Метод вызывающий дефрагментацию системы
     */
    void defragmentation();
}