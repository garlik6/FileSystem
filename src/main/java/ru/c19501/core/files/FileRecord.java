package ru.c19501.core.files;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import ru.c19501.core.files.JsonRelated.Views;
import ru.c19501.exceptions.CoreException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Setter
@Getter
public class FileRecord {
    public boolean isDeleted() {
        return fileStatus == FileStatus.DELETED;
    }

    public void freeSpace() {
        fileStatus = FileStatus.EMPTY_SPACE;
    }

    @Getter
    public enum FileStatus {
        DELETED, NOT_DELETED, EMPTY_SPACE
    }

    @JsonView(Views.Internal.class)
    private FileStatus fileStatus;

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

    public FileRecord(Segment.NewFileParams fileParams, int firstBlock) {
        fileStatus = FileStatus.EMPTY_SPACE;
        this.fileName = fileParams.name;
        this.fileType = fileParams.type;
        this.firstBlock = firstBlock;
        this.volumeInBlocks = fileParams.volumeInBlocks;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.creationDate = dtf.format(LocalDateTime.now());
    }

    public FileRecord(String fileName, String fileType, int firstBlock, int volumeInBlocks) {
        fileStatus = FileStatus.EMPTY_SPACE;
        this.fileName = fileName;
        this.fileType = fileType;
        this.firstBlock = firstBlock;
        this.volumeInBlocks = volumeInBlocks;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.creationDate = dtf.format(LocalDateTime.now());
    }

    //
    public FileRecord(int firstBlock) {
        fileStatus = FileStatus.EMPTY_SPACE;
        this.fileName = "";
        this.fileType = "";
        this.firstBlock = firstBlock;
        this.volumeInBlocks = 0;
        this.creationDate = "";
    }


    public void deleteFile() throws CoreException {
        if (fileStatus == FileStatus.EMPTY_SPACE) {
            throw new CoreException("deleting free space");
        }
        if (fileStatus == FileStatus.DELETED) {
            throw new CoreException("deleting deleted file");
        }
        fileStatus = FileStatus.DELETED;
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

    @JsonIgnore
    public boolean isDeletedOrFree() {
        return fileStatus == FileStatus.EMPTY_SPACE || fileStatus == FileStatus.DELETED;
    }
}
