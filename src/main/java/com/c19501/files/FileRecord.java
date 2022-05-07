package com.c19501.files;

import lombok.Getter;
import lombok.Setter;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class FileRecord {
    private boolean isDeleted;
    private String fileName;
    private String fileType;
    private int firstBlock;
    private int volumeInBlocks;
    private final String creationDate;

    public FileRecord(String fileName, String fileType, int firstBlock, int volumeInBlocks) {
        isDeleted = false;
        this.fileName = fileName;
        this.fileType = fileType;
        this.firstBlock = firstBlock;
        this.volumeInBlocks = volumeInBlocks;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.creationDate = dtf.format(LocalDateTime.now());
    }

    public FileRecord() {
        this.isDeleted = false;
        this.fileName = "";
        this.fileType = "";
        this.firstBlock = 0;
        this.volumeInBlocks = 0;
        this.creationDate = "";
    }

    public int getFirstBlock() {
        return firstBlock;
    }

    public boolean doesFileRecordFit(int amountOfBlocks) {
        return volumeInBlocks < (amountOfBlocks - firstBlock + 1) && (firstBlock - 1) < amountOfBlocks;
    }

    public void deleteFile() {
        isDeleted = true;
    }
}
