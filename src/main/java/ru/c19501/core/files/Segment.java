package ru.c19501.core.files;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
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


    public String addFileRecord(String name, String type, int volumeInBlocks) throws DefragmentationNeeded {
        String id = "";
        if (freeAndDeletedSpace < volumeInBlocks) throw new ArrayIndexOutOfBoundsException("Not enough space left");
        mergeDeletedFiles();
        Optional<FileRecord> findFileRecord = findFirstFreeSpaceOfLength(volumeInBlocks);

        if (findFileRecord.isPresent() && findFileRecord.get().getVolumeInBlocks() == volumeInBlocks) {
            FileRecord foundFileRecord = findFileRecord.get();
            foundFileRecord.setFileName(name);
            foundFileRecord.setFileType(type);
            foundFileRecord.setDeleted(false);
            foundFileRecord.updateDate();
            foundFileRecord.setId(UUID.randomUUID().toString());
            id = foundFileRecord.getId();
        }
        if (findFileRecord.isPresent() && findFileRecord.get().getVolumeInBlocks() > volumeInBlocks) {
            FileRecord foundFileRecord = findFileRecord.get();
            int reduction = foundFileRecord.getVolumeInBlocks() - volumeInBlocks;
            foundFileRecord.reduceVolume(reduction);
            foundFileRecord.setDeleted(false);
            id = foundFileRecord.getId();
            fileRecords.stream().filter(fileRecord -> fileRecord.getNumber() > foundFileRecord.getNumber())
                    .forEach(fileRecord ->
                            fileRecord.setNumber(fileRecord.getNumber() + 1));
            FileRecord additionalEmptyFileRecord = getAdditionalFileRecord(findFileRecord.orElseThrow(), reduction);
            freeAndDeletedSpace -= findFileRecord.get().getVolumeInBlocks();
            fileRecords.add(additionalEmptyFileRecord);
            fileRecords.sort(Comparator.comparing(FileRecord::getNumber));
        }
        if (findFileRecord.isEmpty() && freeSpace >= volumeInBlocks) {
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
        if (Objects.equals(id, "")) {
            throw new DefragmentationNeeded();
        }
        return id;
    }

    private FileRecord getAdditionalFileRecord(FileRecord foundFileRecord, int reduction) {
        FileRecord additionalEmptyFileRecord = new FileRecord("", "", foundFileRecord.getFirstBlock() + foundFileRecord.getVolumeInBlocks(), reduction);
        additionalEmptyFileRecord.setNumber(foundFileRecord.getNumber() + 1);
        additionalEmptyFileRecord.deleteFile();
        return additionalEmptyFileRecord;
    }

    private Optional<FileRecord> findFirstFreeSpaceOfLength(int volumeInBlocks) {
        return fileRecords.stream().filter(fileRecord -> fileRecord.isDeleted() && fileRecord.getVolumeInBlocks() >= volumeInBlocks).findFirst();
    }

    private void mergeDeletedFiles() {
        while (anyDeletedFilesNotSingle()) {
            int firstInRow = getHeadInRow();
            int tailEnd = getTailEnd();
            int count = tailEnd - firstInRow;
            List<FileRecord> toDelete = fileRecords.stream().filter(fileRecord -> fileRecord.getNumber() > firstInRow && fileRecord.getNumber() <= tailEnd).toList();
            int volume = toDelete.stream().mapToInt(FileRecord::getVolumeInBlocks).sum();
            for (int i = 0; i < count; i++) {
                fileRecords.remove(toDelete.get(i));
            }
            try {
                fileRecords.stream().filter(fileRecord -> fileRecord.getNumber() == firstInRow).findFirst().orElseThrow().addVolume(volume);
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
            for (int i = firstInRow + 1; i < fileRecords.size(); i++) {
                fileRecords.get(i).setNumber(fileRecords.get(i).getNumber() - count);
            }
        }
    }

    private int getTailEnd() {
        int tailEnd;
        try {
            tailEnd = fileRecords.stream().filter(this::isInTail).filter(fileRecord -> !fileRecords.get(fileRecord.getNumber() + 1).isDeleted()).findFirst().orElseThrow().getNumber();

        } catch (NoSuchElementException e) {
            tailEnd = fileRecords.size() - 1;
        }
        return tailEnd;
    }


    private boolean isInTail(FileRecord fileRecord) {
        return fileRecord.isDeleted() && fileRecords.get(fileRecord.getNumber() - 1).isDeleted();
    }


    private int getHeadInRow() {
        // get without is present because we checked presence in the loop condition
        return fileRecords.stream().filter(this::isFirstInRow).findFirst().get().getNumber();
    }

    private boolean isFirstInRow(FileRecord fileRecord) {
        return fileRecord.isDeleted() && fileRecord.getNumber() != fileRecords.size() - 1 && fileRecords.get(fileRecord.getNumber() + 1).isDeleted();
    }


    private boolean anyDeletedFilesNotSingle() {
        return fileRecords.stream().filter(FileRecord::isDeleted).anyMatch(fileRecord -> {

                    if (fileRecord.getNumber() == fileRecords.size() - 1) {
                        return false;
                    } else {
                        return fileRecords.get(fileRecord.getNumber() + 1).isDeleted();
                    }
                }
        );

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

    public FileRecord findFileById(String id) {
        return fileRecords.stream().filter(fileRecord -> Objects.equals(fileRecord.getId(), id)).findFirst().orElseThrow();
    }

    public List<FileRecord> findFilesByCondition(Predicate<FileRecord> predicate) {
        return fileRecords.stream().filter(predicate).toList();
    }

    public static class DefragmentationNeeded extends Exception {
    }
}
