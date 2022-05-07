package com.c19501.files;

import com.c19501.config.ConfigLoader;

import java.io.File;
import java.util.*;

public class Segment {
    private final int number;
    private final int amountOfBlocks;
    private int allFreeSpace;
    private final List<FileRecord> fileRecords = new ArrayList<>();

    public Segment() {
        number = 0;
        amountOfBlocks = 0;
    }

    public Segment(int number, int amountOfBlocks) {
        this.number = number;
        this.amountOfBlocks = amountOfBlocks;
        this.allFreeSpace = amountOfBlocks;
    }

    public static Segment createSegment(int number) {
        Properties properties = ConfigLoader.load(new File("src/main/resources/config.properties"));
        int amountOfBlocks = Integer.parseInt(properties.getProperty("fs.maxBlockCountInSegment"));
        return new Segment(number, amountOfBlocks);
    }

    public void addFileRecord(FileRecord fileRecord) {
        if (fileRecord.doesFileRecordFit(amountOfBlocks)) {
            allFreeSpace -= fileRecord.getVolumeInBlocks();
            fileRecords.add(fileRecord);
            fileRecords.sort(Comparator.comparing(FileRecord::getFirstBlock));
        } else throw new IllegalArgumentException();
    }

    public void deleteFileRecord(int number){
        fileRecords.get(number).deleteFile();
    }


    public void getFreeSpaceRemained(){

    }























    public FileRecord getFileRecordByNumber(int number) {
        return fileRecords.get(number);
    }

    public int getNumber() {
        return number;
    }

    public int getAmountOfBlocks() {
        return amountOfBlocks;
    }

    public List<FileRecord> getFileRecords() {
        return fileRecords;
    }
}
