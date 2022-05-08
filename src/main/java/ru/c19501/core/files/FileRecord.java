package ru.c19501.core.files;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Setter
@Getter
public class FileRecord {
    @JsonView(Views.Public.class)
    private boolean deleted;

    @JsonView(Views.Public.class)
    private String fileName;


    @JsonView(Views.Public.class)
    private String fileType;

    @JsonView(Views.Internal.class)
    private int firstBlock;

    @JsonView(Views.Public.class)
    private int volumeInBlocks;

    @JsonView(Views.Public.class)
    private String creationDate;

    @JsonView(Views.Internal.class)
    private int number;

    @JsonView(Views.Public.class)
    private String id = UUID.randomUUID().toString();

    public FileRecord(String fileName, String fileType, int firstBlock, int volumeInBlocks) {
        deleted = false;
        this.fileName = fileName;
        this.fileType = fileType;
        this.firstBlock = firstBlock;
        this.volumeInBlocks = volumeInBlocks;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.creationDate = dtf.format(LocalDateTime.now());
    }
//
    public FileRecord() {
        this.deleted = false;
        this.fileName = "";
        this.fileType = "";
        this.firstBlock = 0;
        this.volumeInBlocks = 0;
        this.creationDate = "";
    }


    public boolean doesFileRecordFit(int amountOfBlocks) {
        return volumeInBlocks < (amountOfBlocks - firstBlock + 1) && (firstBlock - 1) < amountOfBlocks;
    }

    public void deleteFile() {
        deleted = true;
    }

    public void addVolume(int addition) {
        volumeInBlocks += addition;
    }

    public void reduceVolume(int reduction) {
        volumeInBlocks -= reduction;
    }


    public void updateDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.creationDate = dtf.format(LocalDateTime.now());
    }
}
