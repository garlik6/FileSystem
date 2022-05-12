package ru.c19501.core.files;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.c19501.core.config.ConfigLoader;
import ru.c19501.exceptions.CoreException;

import java.util.*;
import java.util.function.Predicate;

public class Segment {
    @JsonView(Views.Internal.class)
    private final int number;
    @JsonAlias("SPACE")
    @JsonView(Views.Public.class)
    private final int amountOfBlocks;
    @JsonView(Views.Public.class)
    @JsonAlias("FREE_SPACE")
    private int freeAndDeletedSpace;
    @JsonView(Views.Public.class)
    private int freeSpace;
    @JsonAlias("FILES")
    @JsonView(Views.Public.class)
    private final List<FileRecord> fileRecords = new ArrayList<>();

    @Data
    @AllArgsConstructor
    static class NewFileParams{
        String name;
        String type;
        int volumeInBlocks;
    }

    public Segment() {
        number = 0;
        amountOfBlocks = 0;
    }

    public Segment(int number, int amountOfBlocks) {
        this.number = number;
        this.amountOfBlocks = amountOfBlocks;
        this.freeAndDeletedSpace = amountOfBlocks;
        freeSpace = amountOfBlocks;
    }

    public static Segment createSegment(int number) {
        Properties properties = ConfigLoader.properties;
        int amountOfBlocks = Integer.parseInt(properties.getProperty("fs.maxBlockCountInSegment"));
        return new Segment(number, amountOfBlocks);
    }


    public String addFileRecord(String name, String type, int volumeInBlocks) throws CoreException {
        FileAdder fileAdder = new FileAdder(this);
        NewFileParams fileParams = new NewFileParams(name, type, volumeInBlocks);
        return fileAdder.addFileRecord(fileParams);
    }

    public void deleteFileRecordById(String id) {
        FileRecord fileRecord = findFileById(id);
        if (fileRecord.isDeleted())
            throw new IllegalStateException("trying to delete file that is already deleted");
        fileRecord.deleteFile();
        freeAndDeletedSpace += fileRecord.getVolumeInBlocks();
    }

    public int getFreeAndDeletedSpace() {
        return freeAndDeletedSpace;
    }

    public FileRecord getFileRecordByNumber(int number) {
        return fileRecords.get(number);
    }

    public int getAmountOfBlocks() {
        return amountOfBlocks;
    }

    @JsonGetter("fileRecords")
    public List<FileRecord> getFileRecordsCopy() {
        return new ArrayList<>(fileRecords);
    }

    List<FileRecord> getFileRecords() {
        return fileRecords;
    }


    public FileRecord findFileById(String id) {
        return fileRecords.stream().filter(fileRecord -> Objects.equals(fileRecord.getId(), id)).findFirst().orElseThrow();
    }

    public List<FileRecord> findFilesByCondition(Predicate<FileRecord> predicate) {
        return fileRecords.stream().filter(predicate).toList();
    }

    public void setFreeAndDeletedSpace(int freeAndDeletedSpace) {
        this.freeAndDeletedSpace = freeAndDeletedSpace;
    }

    public void setFreeSpace(int freeSpace) {
        this.freeSpace = freeSpace;
    }

    public int getFreeSpace() {
        return freeSpace;
    }

}
