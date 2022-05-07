package ru.c19501.core;

public interface FileSystem {

    void save();

    void load();

    void addFileInSegment(String name, String type, int length, int segment);

    void deleteFileInSegmentById(int segment, int fileId);

    int getFreeSpaceInSegment(int segment);

    void print();

}
