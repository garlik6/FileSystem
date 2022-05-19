package ru.c19501.system;

import ru.c19501.exceptions.CoreException;


public interface FileSystem {
    /**
     * Saves file system in format, path specified in config.properties
     */
    void save();
    /**  Loads system in format, path specified in config.properties
     * @return is loading successful
     */
    boolean load();
    /**
     * @param name    name of the file, being added
     * @param type    type of the file, being added
     * @param length  volume in blocks 0f the file, being added
     * @return returns unique id of the added file
     */


    String addFile(String name, String type, int length) throws CoreException;
    /**
     * @param id      unique id of the file to delete
     */

    void deleteFileById(String id) throws IllegalStateException, CoreException;
    /**
     * @param id      unique id of the file
     * @return JSON representation of that particular file
     */

    String findFileById(String id);

    /**
     * @param name    name of the file
     * @return JSON representation of array of files with this name
     */
    String findFilesByName(String name);
    /**
     * @param type    desired file type (extension)
     * @return returns JSON representation of array of files with this type
     */
    String findFilesByType(String type);

    /**
     * @return volume in blocks of deleted files + not yet used blocks
     */
    int getFreeSpace();

    /**
     * @return returns JSON representation of array of all files in this segment
     */
    String retrieveAllFiles();
}
