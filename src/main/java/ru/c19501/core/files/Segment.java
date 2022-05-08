package ru.c19501.core.files;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonView;
import ru.c19501.core.config.ConfigLoader;

import java.io.File;
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

    public String addFileRecord(String name, String type, int volumeInBlocks) {
        String id = "";
        if (freeAndDeletedSpace < volumeInBlocks) throw new ArrayIndexOutOfBoundsException("Not enough space left");
        mergeDeletedFiles();
        Optional<FileRecord> foundFileRecord = findFirstFreeSpaceOfLength(volumeInBlocks);
        if (foundFileRecord.isPresent() && foundFileRecord.get().getVolumeInBlocks() == volumeInBlocks) {
            foundFileRecord.get().setFileName(name);
            foundFileRecord.get().setFileType(type);
            foundFileRecord.get().setDeleted(false);
            foundFileRecord.get().updateDate();
            id = foundFileRecord.get().getId();
        }
        if (foundFileRecord.isPresent() && foundFileRecord.get().getVolumeInBlocks() > volumeInBlocks) {
            int reduction = foundFileRecord.get().getVolumeInBlocks() - volumeInBlocks;
            foundFileRecord.get().reduceVolume(reduction);
            FileRecord additionalEmptyFileRecord = new FileRecord("", "", foundFileRecord.get().getFirstBlock() + foundFileRecord.get().getVolumeInBlocks(), reduction);
            additionalEmptyFileRecord.setNumber(foundFileRecord.get().getNumber() + 1);
            additionalEmptyFileRecord.deleteFile();
            foundFileRecord.get().setDeleted(false);
            fileRecords.stream().filter(fileRecord -> fileRecord.getNumber() > foundFileRecord.get().getNumber()).forEach(fileRecord -> fileRecord.setNumber(fileRecord.getNumber() + 1));
            fileRecords.add(additionalEmptyFileRecord);
            fileRecords.sort(Comparator.comparing(FileRecord::getNumber));
            freeAndDeletedSpace -= foundFileRecord.get().getVolumeInBlocks();
            id = foundFileRecord.get().getId();

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
            id = newFileRecord.getId();
        }
        return id;
    }

    private Optional<FileRecord> findFirstFreeSpaceOfLength(int volumeInBlocks) {
        return fileRecords.stream().filter(fileRecord -> fileRecord.isDeleted() && fileRecord.getVolumeInBlocks() >= volumeInBlocks).findFirst();
    }

    private void mergeDeletedFiles() {
        while (anyDeletedFilesNotSingle()) {
            // get without is present because we checked presence in the loop condition
            int firstInRow = fileRecords.stream().filter(fileRecord -> fileRecord.isDeleted() && fileRecords.get(fileRecord.getNumber() + 1).isDeleted()).findFirst().get().getNumber();
            int volume = fileRecords.stream().filter(fileRecord -> fileRecord.isDeleted() && fileRecords.get(fileRecord.getNumber() - 1).isDeleted()).takeWhile(fileRecord -> fileRecords.get(fileRecord.getNumber() - 1).isDeleted()).mapToInt(FileRecord::getVolumeInBlocks).sum();
            int count = (int) fileRecords.stream().filter(fileRecord -> fileRecord.isDeleted() && fileRecords.get(fileRecord.getNumber() - 1).isDeleted()).takeWhile(fileRecord -> fileRecords.get(fileRecord.getNumber() - 1).isDeleted()).mapToInt(FileRecord::getVolumeInBlocks).count();

            List<FileRecord> toDelete = fileRecords.stream().filter(fileRecord -> fileRecord.isDeleted() && fileRecords.get(fileRecord.getNumber() - 1).isDeleted()).takeWhile(fileRecord -> fileRecords.get(fileRecord.getNumber() - 1).isDeleted()).toList();

            for (int i = 0; i < count; i++) {
                fileRecords.remove(toDelete.get(i));
            }

            try {
                fileRecords.stream().filter(FileRecord::isDeleted).findFirst().orElseThrow().addVolume(volume);
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
            for (int i = firstInRow + 1; i < fileRecords.size(); i++) {
                fileRecords.get(i).setNumber(fileRecords.get(i).getNumber() - count);
            }
        }
    }

    private boolean anyDeletedFilesNotSingle() {
        return fileRecords.stream().filter(FileRecord::isDeleted).anyMatch(fileRecord -> fileRecords.get(fileRecord.getNumber() + 1).isDeleted());
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

    public int getNumber() {
        return number;
    }

    public int getAmountOfBlocks() {
        return amountOfBlocks;
    }

    public List<FileRecord> getFileRecords() {
        return fileRecords;
    }

    public FileRecord findFileById(String id){return fileRecords.stream().filter(fileRecord -> Objects.equals(fileRecord.getId(), id)).findFirst().orElseThrow();}

    public List<FileRecord> findFilesByCondition(Predicate<FileRecord> predicate) {
        return fileRecords.stream().filter(predicate).toList();
    }
}
