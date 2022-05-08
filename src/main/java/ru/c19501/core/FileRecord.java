package ru.c19501.core;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/*
*
* DTO for storing Json results of queries to core
*
* */
@Getter
@Setter
@ToString
public class FileRecord {
    boolean isDeleted;
    String fileName;
    String fileType;
    int volumeInBlocks;
    String creationDate;
    String id;
    boolean deleted;
}
