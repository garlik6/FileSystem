package ru.c19501.core.files;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import ru.c19501.config.ConfigLoader;
import ru.c19501.core.files.JsonRelated.Views;
import ru.c19501.exceptions.CoreException;

import java.util.*;

public class Segment {
    @Getter
    @JsonView(Views.Internal.class)
    private int maxRecordAmount;
    @JsonView(Views.Internal.class)
    private int startingBlock;
    @JsonView(Views.Public.class)
    private List<FileRecord> fileRecords = new ArrayList<>();

    public int getStartingBlock() {
        return startingBlock;
    }

    public void setStartingBlock(int startingBlock) {
        this.startingBlock = startingBlock;
    }

    public int currentDeletedAndNotRecords() {
        return (int) fileRecords.stream().filter(fileRecord -> fileRecord.getFileStatus() == FileRecord.FileStatus.DELETED || fileRecord.getFileStatus() == FileRecord.FileStatus.NOT_DELETED).count();
    }

    @Data
    @AllArgsConstructor
    public static class NewFileParams {
        String name;
        String type;
        int volumeInBlocks;
    }

    public Segment() {

    }

    public Segment(int maxRecordAmount) {
        startingBlock = 0;
        this.maxRecordAmount = maxRecordAmount;
    }

    public Segment(int maxRecordAmount, int startingBlock, ArrayList<FileRecord> fileRecords) {
        this.maxRecordAmount = maxRecordAmount;
        this.startingBlock = startingBlock;
        this.fileRecords = fileRecords;
    }

    public static Segment createSegment() {
        Properties properties = ConfigLoader.properties;
        int maxRecordAmount = Integer.parseInt(properties.getProperty("fs.defaultMaxRecordAmountInSegment"));
        return new Segment(maxRecordAmount);
    }

    public void sortFileRecords() {
        fileRecords.sort(Comparator.comparing(FileRecord::getNumber));
    }

    @JsonIgnore
    public int getFileRecordsSize() {
        return fileRecords.size();
    }

    public void addFileRecord(FileRecord newFileRecord) {
        fileRecords.add(newFileRecord);
    }

    public FileRecord getFileRecordByNumber(int number) {
        return fileRecords.get(number);
    }

    @JsonGetter("fileRecords")
    public List<FileRecord> getFileRecordsCopy() {
        return new ArrayList<>(fileRecords);
    }

    @JsonIgnore
    public List<FileRecord> getFileRecords() {
        return fileRecords;
    }

    public FileRecord findFileById(String id) {
        return fileRecords.stream().filter(fileRecord -> Objects.equals(fileRecord.getId(), id)).findFirst().orElseThrow();
    }

    public void updateMaxRecordAmount(int maxRecordAmount) throws CoreException {
        if (this.fileRecords.isEmpty()) {
            this.maxRecordAmount = maxRecordAmount;
            return;
        }
        throw new CoreException("trying to change record count in not empty segment");
    }

    public void updateRestOfFiles(FileRecord foundFileRecord) {
        fileRecords.stream().filter(fileRecord -> fileRecord.getNumber() > foundFileRecord.getNumber())
                .forEach(fileRecord -> fileRecord.setNumber(fileRecord.getNumber() + 1));
    }

    @JsonIgnore
    public int getReadyToAddSpace() {
        return fileRecords.stream().filter(FileRecord::isDeletedOrFree).mapToInt(FileRecord::getVolumeInBlocks).sum();
    }

    public void nullifyFileRecords() {
        fileRecords.clear();
    }
}
