package ru.c19501.core.files;

import lombok.AllArgsConstructor;
import ru.c19501.exceptions.CoreException;

import java.util.*;

@AllArgsConstructor
public class FileAdder {

    Segment segment;


    public String addFileRecord(Segment.NewFileParams fileParams) throws CoreException {
        if (segment.getFreeAndDeletedSpace() < fileParams.volumeInBlocks) {
            throw new ArrayIndexOutOfBoundsException("Not enough space left");
        }
        mergeDeletedFiles();
        Optional<FileRecord> findFileRecord = findFirstFreeSpaceOfLength(fileParams.volumeInBlocks);
        if (findFileRecord.isPresent()) {
            exchangeFoundToNew(findFileRecord.get(), fileParams);
            return findFileRecord.get().getId();
        }
        if (segment.getFreeSpace() >= fileParams.volumeInBlocks) {
            return addNewFileRecord(new FileRecord(fileParams, getLastBlock()));
        }
        throw new CoreException("There is not enough space, defragmentation is needed");
    }

    private void exchangeFoundToNew(FileRecord foundFileRecord, Segment.NewFileParams fileParams) {
        if (foundFileRecord.getVolumeInBlocks() == fileParams.volumeInBlocks) {
            transformFoundToNew(fileParams, foundFileRecord);
        }
        if (foundFileRecord.getVolumeInBlocks() > fileParams.volumeInBlocks) {
            int reduction = foundFileRecord.getVolumeInBlocks() - fileParams.volumeInBlocks;
            addAdditionalFileRecord(foundFileRecord, reduction);
            updateRestOfFiles(foundFileRecord);
            adjustFoundFile(foundFileRecord, reduction);

        }
    }

    private String addNewFileRecord(FileRecord newFileRecord) {
        newFileRecord.setNumber(segment.getFileRecords().size());
        segment.getFileRecords().add(newFileRecord);
        segment.setFreeAndDeletedSpace(segment.getFreeAndDeletedSpace() - newFileRecord.getVolumeInBlocks());
        segment.setFreeSpace(segment.getFreeSpace() - newFileRecord.getVolumeInBlocks());
        return newFileRecord.getId();
    }

    private int getLastBlock() {
        int lastBlock;
        if (segment.getFileRecords().isEmpty()) {
            lastBlock = 0;
        } else {
            lastBlock = segment.getFileRecords()
                    .get(segment.getFileRecords().size() - 1)
                    .getFirstBlock() + segment.getFileRecords()
                    .get(segment.getFileRecords().size() - 1).getVolumeInBlocks();
        }
        return lastBlock;
    }

    private void updateRestOfFiles(FileRecord foundFileRecord) {
        segment.getFileRecords().stream().filter(fileRecord -> fileRecord.getNumber() > foundFileRecord.getNumber() + 1)
                .forEach(fileRecord -> fileRecord.setNumber(fileRecord.getNumber() + 1));
        segment.getFileRecords().sort(Comparator.comparing(FileRecord::getNumber));
    }

    private void addAdditionalFileRecord(FileRecord findFileRecord, int reduction) {
        FileRecord additionalEmptyFileRecord = getAdditionalFileRecord(findFileRecord, reduction);
        segment.setFreeAndDeletedSpace(segment.getFreeAndDeletedSpace() - findFileRecord.getVolumeInBlocks());
        segment.getFileRecords().add(additionalEmptyFileRecord);
    }

    private void adjustFoundFile(FileRecord foundFileRecord, int reduction) {
        foundFileRecord.reduceVolume(reduction);
        foundFileRecord.setDeleted(false);
    }

    private void transformFoundToNew(Segment.NewFileParams fileParams, FileRecord foundFileRecord) {
        foundFileRecord.setFileName(fileParams.name);
        foundFileRecord.setFileType(fileParams.type);
        foundFileRecord.setDeleted(false);
        foundFileRecord.updateDate();
        foundFileRecord.setId(UUID.randomUUID().toString());
    }

    private FileRecord getAdditionalFileRecord(FileRecord foundFileRecord, int reduction) {
        FileRecord additionalEmptyFileRecord = new FileRecord("", "", foundFileRecord.getFirstBlock() + foundFileRecord.getVolumeInBlocks(), reduction);
        additionalEmptyFileRecord.setNumber(foundFileRecord.getNumber() + 1);
        additionalEmptyFileRecord.deleteFile();
        return additionalEmptyFileRecord;
    }

    private Optional<FileRecord> findFirstFreeSpaceOfLength(int volumeInBlocks) {
        return segment.getFileRecords().stream()
                .filter(fileRecord -> fileRecord.isDeleted() && fileRecord.getVolumeInBlocks() >= volumeInBlocks)
                .findFirst();
    }

    private void mergeDeletedFiles() {
        while (anyDeletedFilesNotSingle()) {
            int firstInRow = getHeadInRow();
            int tailEnd = getTailEnd();
            int count = tailEnd - firstInRow;
            List<FileRecord> toDelete = segment.getFileRecords().stream()
                    .filter(fileRecord -> fileRecord.getNumber() > firstInRow && fileRecord.getNumber() <= tailEnd).toList();
            int volume = toDelete.stream().mapToInt(FileRecord::getVolumeInBlocks).sum();
            for (int i = 0; i < count; i++) {
                segment.getFileRecords().remove(toDelete.get(i));
            }
                segment.getFileRecords().stream()
                        .filter(fileRecord -> fileRecord.getNumber() == firstInRow).findFirst().orElseThrow().addVolume(volume);
            for (int i = firstInRow + 1; i < segment.getFileRecords().size(); i++) {
                segment.getFileRecords().get(i).setNumber(segment.getFileRecords().get(i).getNumber() - count);
            }
        }
    }

    private int getTailEnd() {
        int tailEnd;
            tailEnd = segment.getFileRecords().stream()
                    .filter(this::isInTail)
                    .filter(fileRecord -> {
                        if (fileRecord.getNumber() == segment.getFileRecords().size() - 1) {
                            return true;
                        }
                        return !segment.getFileRecords().get(fileRecord.getNumber() + 1).isDeleted();
                    })
                    .findFirst().orElseThrow().getNumber();
        return tailEnd;
    }


    private boolean isInTail(FileRecord fileRecord) {
        return fileRecord.isDeleted() && segment.getFileRecords().get(fileRecord.getNumber() - 1).isDeleted();
    }


    private int getHeadInRow() {
        return segment.getFileRecords().stream().filter(this::isFirstInRow).findFirst().orElseThrow().getNumber();
    }

    private boolean isFirstInRow(FileRecord fileRecord) {
        return fileRecord.isDeleted() &&
                (fileRecord.getNumber() != (segment.getFileRecords().size() - 1)) &&
                segment.getFileRecords().get(fileRecord.getNumber() + 1).isDeleted();
    }


    private boolean anyDeletedFilesNotSingle() {
        return segment.getFileRecords().stream().filter(FileRecord::isDeleted).anyMatch(fileRecord -> {

                    if (fileRecord.getNumber() == segment.getFileRecords().size() - 1) {
                        return false;
                    } else {
                        return segment.getFileRecords().get(fileRecord.getNumber() + 1).isDeleted();
                    }
                }
        );

    }
}
