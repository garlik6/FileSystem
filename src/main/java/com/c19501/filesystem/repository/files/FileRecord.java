package com.c19501.filesystem.repository.files;

import java.time.*;
import java.time.format.DateTimeFormatter;

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

    public void deleteFile(){
        isDeleted = true;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public int getVolumeInBlocks() {
        return volumeInBlocks;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFirstBlock(int firstBlock) {
        this.firstBlock = firstBlock;
    }

    public void setVolumeInBlocks(int volumeInBlocks) {
        this.volumeInBlocks = volumeInBlocks;
    }
}
