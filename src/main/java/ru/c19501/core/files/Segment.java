package ru.c19501.core.files;

import ru.c19501.core.config.ConfigLoader;

import java.io.File;
import java.util.*;

public class Segment {
    private final int number;
    private final int amountOfBlocks;
    private int freeAndDeletedSpace;
    private int freeSpace;
    private final List<FileRecord> fileRecords = new ArrayList<>();

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
        Properties properties = ConfigLoader.load(new File("src/main/resources/config.properties"));
        int amountOfBlocks = Integer.parseInt(properties.getProperty("fs.maxBlockCountInSegment"));
        return new Segment(number, amountOfBlocks);
    }

    public void insertFileRecord(FileRecord fileRecord) {
        if (fileRecord.doesFileRecordFit(amountOfBlocks)) {
            freeAndDeletedSpace -= fileRecord.getVolumeInBlocks();
            freeSpace -= fileRecord.getVolumeInBlocks();
            fileRecord.setNumber(fileRecords.size());
            fileRecords.add(fileRecord);
            fileRecords.sort(Comparator.comparing(FileRecord::getFirstBlock));
        } else throw new IllegalArgumentException();
    }

    public void addFileRecord(String name, String type, int volumeInBlocks) {
        if (freeAndDeletedSpace < volumeInBlocks)
            throw new ArrayIndexOutOfBoundsException("Not enough space left");
        mergeDeletedFiles();
        Optional<FileRecord> foundFileRecord = findFirstFreeSpaceOfLength(volumeInBlocks);
        if (foundFileRecord.isPresent() && foundFileRecord.get().getVolumeInBlocks() == volumeInBlocks) {
            foundFileRecord.get().setFileName(name);
            foundFileRecord.get().setFileType(type);
        }
        if (foundFileRecord.isPresent() && foundFileRecord.get().getVolumeInBlocks() < volumeInBlocks) {
            int reduction = foundFileRecord.get().getVolumeInBlocks() - volumeInBlocks;
            foundFileRecord.get().reduceVolume(reduction);
            FileRecord additionalEmptyFileRecord = new FileRecord(name, type, foundFileRecord.get().getFirstBlock() + foundFileRecord.get().getVolumeInBlocks(), reduction);
            additionalEmptyFileRecord.setNumber(foundFileRecord.get().getNumber() + 1);
            fileRecords.stream().filter(fileRecord -> fileRecord.getNumber() > foundFileRecord.get().getNumber()).forEach(fileRecord -> fileRecord.setNumber(fileRecord.getNumber() + 1));
        }
        if (foundFileRecord.isEmpty() && freeSpace >= volumeInBlocks) {
            int lastBlock;
            if (fileRecords.isEmpty()) {
                lastBlock = 0;
            } else {
                lastBlock = fileRecords.get(fileRecords.size() - 1).getFirstBlock() + fileRecords.get(fileRecords.size() - 1).getVolumeInBlocks();
            }
            FileRecord newFileRecord = new FileRecord(name, type, lastBlock, volumeInBlocks);
            newFileRecord.setNumber(fileRecords.size());
            fileRecords.add(newFileRecord);
            freeAndDeletedSpace -= newFileRecord.getVolumeInBlocks();
            freeSpace -= newFileRecord.getVolumeInBlocks();
        }
    }

    private Optional<FileRecord> findFirstFreeSpaceOfLength(int volumeInBlocks) {
        return fileRecords.stream().filter(fileRecord -> fileRecord.isDeleted() && fileRecord.getVolumeInBlocks() >= volumeInBlocks).findFirst();
    }

    private void mergeDeletedFiles() {
        while (anyDeletedFilesNotSingle()) {
            int volume = fileRecords.stream().filter(FileRecord::isDeleted).takeWhile(fileRecord -> fileRecords.get(fileRecord.getNumber() - 1).isDeleted()).mapToInt(FileRecord::getVolumeInBlocks).sum();
            fileRecords.stream().filter(FileRecord::isDeleted).takeWhile(fileRecord -> fileRecords.get(fileRecord.getNumber() - 1).isDeleted()).forEach(fileRecords::remove);
            try {
                fileRecords.stream().filter(FileRecord::isDeleted).findFirst().orElseThrow().addVolume(volume);

            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean anyDeletedFilesNotSingle() {
        return fileRecords.stream().filter(FileRecord::isDeleted).anyMatch(fileRecord -> fileRecords.get(fileRecord.getNumber() + 1).isDeleted());
    }

    public void deleteFileRecord(int number) {
        if(fileRecords.get(number).isDeleted())
            throw new IllegalStateException("trying to delete file that is already deleted");
        fileRecords.get(number).deleteFile();
        freeAndDeletedSpace += fileRecords.get(number).getVolumeInBlocks();
    }

    public int getFreeSpaceRemained() {
        return freeAndDeletedSpace;
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
