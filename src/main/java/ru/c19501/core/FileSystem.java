package ru.c19501.core;

import ru.c19501.core.files.Segment;

public interface FileSystem {

    /**
     * Saves file system in format, path specified in config.properties
     */
    void save();

    /**
     * Loads system in format, path specified in config.properties
     */
    void load();

    /**
     * @param name    name of the file, being added
     * @param type    type of the file, being added
     * @param length  volume in blocks 0f the file, being added
     * @param segment segment to add file to(amount of segments is controlled by config.properties)
     * @return returns unique id of the added file
     */
    String addFileInSegment(String name, String type, int length, int segment) throws Segment.DefragmentationNeeded;

    /**
     * @param segment segment to delete file from
     * @param id      unique id of the file to delete
     */
    void deleteFileInSegmentById(int segment, String id);

    /**
     * @param segment segment to find file in
     * @param id      unique id of the file
     * @return JSON representation of that particular file
     */
    String findFileInSegmentById(int segment, String id);

    /**
     * @param segment segment to find files in
     * @param name    name of the file
     * @return JSON representation of array of files with this name
     */
    String findFilesInSegmentByName(int segment, String name);

    /**
     * @param segment segment to find files in
     * @param type    desired file type (extension)
     * @return returns JSON representation of array of files with this type
     */
    String findFilesInSegmentByType(int segment, String type);

    /**
     * @param segment segment to check
     * @return volume in blocks of deleted files + not yet used blocks
     */
    int getFreeSpaceInSegment(int segment);

    /**
     * @param segment segment to get files from
     * @return returns JSON representation of array of all files in this segment
     */
    String retrieveAllFilesFromSegment(int segment);
}
