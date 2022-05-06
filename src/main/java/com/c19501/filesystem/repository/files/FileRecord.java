package com.c19501.filesystem.repository.files;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class FileRecord {
    boolean isDeleted;
    String fileName;
    String fileType;
    int firstBlock;
    int volumeInBlocks;
    String creationDate;

    public FileRecord(String fileName, String fileType, int firstBlock, int volumeInBlocks) {
        isDeleted = false;
        this.fileName = fileName;
        this.fileType = fileType;
        this.firstBlock = firstBlock;
        this.volumeInBlocks = volumeInBlocks;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.creationDate = dtf.format(LocalDateTime.now());
    }

    public int getFirstBlock() {
        return firstBlock;
    }


    public boolean doesFileRecordFit(int amountOfBlocks) {
        return volumeInBlocks < (amountOfBlocks - firstBlock + 1) && (firstBlock - 1) < amountOfBlocks;
    }
}
