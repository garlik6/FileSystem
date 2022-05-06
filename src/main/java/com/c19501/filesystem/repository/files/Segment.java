package com.c19501.filesystem.repository.files;

import com.c19501.filesystem.FileSystem.configLoader.ConfigLoader;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;

public class Segment {
    final int number;
    final int amountOfBlocks;
    ArrayList<FileRecord> fileRecords = new ArrayList<>();


    public Segment() {
        number = 0;
        amountOfBlocks = 0;
    }

    public static Segment createSegment(int number) {
        Properties properties = ConfigLoader.load(new File("src/main/resources/config.properties"));
        int amountOfBlocks = Integer.parseInt(properties.getProperty("fs.maxBlockCountInSegment"));
        return new Segment(number, amountOfBlocks);
    }

    public void addFileRecord(FileRecord fileRecord){
        if(fileRecord.doesFileRecordFit(amountOfBlocks)){
            fileRecords.add(fileRecord);
            fileRecords.sort(Comparator.comparing(FileRecord::getFirstBlock));
        }
        else throw new IllegalArgumentException();
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

    public ArrayList<FileRecord> getFileRecords() {
        return fileRecords;
    }


    public Segment(int number, int amountOfBlocks) {
        this.number = number;
        this.amountOfBlocks = amountOfBlocks;
    }
}
