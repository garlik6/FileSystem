package ru.c19501.core;
import lombok.Data;
/*
*
* DTO for storing Json results of queries to core
*
* */
@Data
public class FileRecord {
    boolean isDeleted;
    String fileName;
    String fileType;
    int volumeInBlocks;
    String creationDate;
    String id;
    boolean deleted;
}
