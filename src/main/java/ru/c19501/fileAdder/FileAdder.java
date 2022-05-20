package ru.c19501.fileAdder;

import lombok.AllArgsConstructor;
import ru.c19501.core.files.FileRecord;
import ru.c19501.core.files.Segment;
import ru.c19501.core.repository.Repository;
import ru.c19501.exceptions.CoreException;

import java.util.*;

@AllArgsConstructor
public class FileAdder {
    Repository repository;
    Segment currentSegment;
    public String addFileRecord(Segment.NewFileParams fileParams) throws CoreException {
        if (repository.getReadyToAddSpace() < fileParams.getVolumeInBlocks()) {
            throw new ArrayIndexOutOfBoundsException("Not enough space left");
        }
        for (int i = 0; i < repository.getMaxSegments(); i++) {
            int deletedAndNot =  repository.getSegmentsCopy().get(i).currentDeletedAndNotRecords();
            if (deletedAndNot < repository.getSegmentsCopy().get(i).getMaxRecordAmount()) {
                currentSegment = repository.getSegment(i);
                break;
            }
            if (repository.getSegmentsCopy().get(i).currentDeletedAndNotRecords() == repository.getSegmentsCopy().get(i).getMaxRecordAmount() && i == repository.getMaxSegments() - 1) {
                throw new IllegalStateException("Not enough file records defragmentation needed");
            }
        }
        mergeDeletedFiles();
        Optional<FileRecord> findFileRecord = findFirstReadyToAddSpaceOfLength(fileParams.getVolumeInBlocks());
        if (findFileRecord.isPresent()) {
            transformFoundToNew(findFileRecord.get(), fileParams);
            return findFileRecord.get().getId();
        }
        if (repository.getFreeSpace() >= fileParams.getVolumeInBlocks()) {
            repository.moveRestOfSegments(repository.getSegmentNumber(currentSegment), fileParams.getVolumeInBlocks());
            return addNewFileRecord(new FileRecord(fileParams, getLastBlock()));
        }
        throw new CoreException("There is not enough space, defragmentation is needed");
    }

    private void transformFoundToNew(FileRecord foundFileRecord, Segment.NewFileParams fileParams){
        if (foundFileRecord.getVolumeInBlocks() == fileParams.getVolumeInBlocks()) {
            exchangeFoundToNew(fileParams, foundFileRecord);
        }
        if (foundFileRecord.getVolumeInBlocks() > fileParams.getVolumeInBlocks()) {
            int reduction = foundFileRecord.getVolumeInBlocks() - fileParams.getVolumeInBlocks();
            currentSegment.updateRestOfFiles(foundFileRecord);
            adjustFoundFile(foundFileRecord, reduction, fileParams);
            addAdditionalFileRecord(foundFileRecord, reduction);
            currentSegment.sortFileRecords();
        }
    }

    private String addNewFileRecord(FileRecord newFileRecord) {
        newFileRecord.setNumber(currentSegment.getFileRecordsSize());
        newFileRecord.setFileStatus(FileRecord.FileStatus.NOT_DELETED);
        currentSegment.addFileRecord(newFileRecord);
        repository.setReadyToAddSpace(repository.getReadyToAddSpace() - newFileRecord.getVolumeInBlocks());
        repository.setFreeSpace(repository.getFreeSpace() - newFileRecord.getVolumeInBlocks());
        return newFileRecord.getId();
    }

    private int getLastBlock() {
        int lastBlock;
        if (currentSegment.getFileRecordsSize() == 0) {
            lastBlock = currentSegment.getStartingBlock();
        } else {
            lastBlock = currentSegment.getFileRecordsCopy()
                    .get(currentSegment.getFileRecordsCopy().size() - 1)
                    .getFirstBlock() + currentSegment.getFileRecordsCopy()
                    .get(currentSegment.getFileRecordsCopy().size() - 1).getVolumeInBlocks();
        }
        return lastBlock;
    }

    private void addAdditionalFileRecord(FileRecord findFileRecord, int reduction) {
        FileRecord additionalEmptyFileRecord = getAdditionalFileRecord(findFileRecord, reduction);
        repository.setReadyToAddSpace(repository.getReadyToAddSpace() - findFileRecord.getVolumeInBlocks());
        currentSegment.addFileRecord(additionalEmptyFileRecord);
    }

    private void adjustFoundFile(FileRecord foundFileRecord, int reduction, Segment.NewFileParams fileParams) {
        exchangeFoundToNew(fileParams, foundFileRecord);
        foundFileRecord.reduceVolume(reduction);
    }

    private void exchangeFoundToNew(Segment.NewFileParams fileParams, FileRecord foundFileRecord) {
        foundFileRecord.setFileName(fileParams.getName());
        foundFileRecord.setFileType(fileParams.getType());
        foundFileRecord.setFileStatus(FileRecord.FileStatus.NOT_DELETED);
        foundFileRecord.updateDate();
        foundFileRecord.setId(UUID.randomUUID().toString());
    }

    private FileRecord getAdditionalFileRecord(FileRecord foundFileRecord, int reduction) {
        FileRecord additionalEmptyFileRecord = new FileRecord("", "", foundFileRecord.getFirstBlock() + foundFileRecord.getVolumeInBlocks(), reduction,1);
        additionalEmptyFileRecord.setNumber(foundFileRecord.getNumber() + 1);
        additionalEmptyFileRecord.freeSpace();
        return additionalEmptyFileRecord;
    }

    private Optional<FileRecord> findFirstReadyToAddSpaceOfLength(int volumeInBlocks) {
        return currentSegment.getFileRecords().stream()
                .filter(fileRecord -> fileRecord.isDeletedOrFree() && fileRecord.getVolumeInBlocks() >= volumeInBlocks)
                .findFirst();
    }

    private void mergeDeletedFiles() {
        while (anyDeletedFilesNotSingle()) {
            int firstInRow = getHeadInRow();
            int tailEnd = getTailEnd();
            int count = tailEnd - firstInRow;
            List<FileRecord> toDelete = currentSegment.getFileRecords().stream()
                    .filter(fileRecord -> fileRecord.getNumber() > firstInRow && fileRecord.getNumber() <= tailEnd).toList();
            int volume = toDelete.stream().mapToInt(FileRecord::getVolumeInBlocks).sum();
            for (int i = 0; i < count; i++) {
                currentSegment.getFileRecords().remove(toDelete.get(i));
            }
            currentSegment.getFileRecords().stream()
                    .filter(fileRecord -> fileRecord.getNumber() == firstInRow).findFirst().orElseThrow().addVolume(volume);
            for (int i = firstInRow + 1; i < currentSegment.getFileRecords().size(); i++) {
                currentSegment.getFileRecords().get(i).setNumber(currentSegment.getFileRecords().get(i).getNumber() - count);
            }
        }
    }

    private int getTailEnd() {
        int tailEnd;
        tailEnd = currentSegment.getFileRecords().stream()
                .filter(this::isInTail)
                .filter(fileRecord -> {
                    if (fileRecord.getNumber() == currentSegment.getFileRecords().size() - 1) {
                        return true;
                    }
                    return !currentSegment.getFileRecords().get(fileRecord.getNumber() + 1).isDeletedOrFree();
                })
                .findFirst().orElseThrow().getNumber();
        return tailEnd;
    }

    private boolean isInTail(FileRecord fileRecord) {
        return fileRecord.isDeletedOrFree() && fileRecord.getNumber() != 0 && currentSegment.getFileRecords().get(fileRecord.getNumber() - 1).isDeletedOrFree();
    }

    private int getHeadInRow() {
        return currentSegment.getFileRecords().stream().filter(this::isFirstInRow).findFirst().orElseThrow().getNumber();
    }

    private boolean isFirstInRow(FileRecord fileRecord) {
        return fileRecord.isDeletedOrFree() &&
                (fileRecord.getNumber() != (currentSegment.getFileRecords().size() - 1)) &&
                currentSegment.getFileRecords().get(fileRecord.getNumber() + 1).isDeletedOrFree();
    }

    private boolean anyDeletedFilesNotSingle() {
        return currentSegment.getFileRecords().stream().filter(FileRecord::isDeletedOrFree).anyMatch(fileRecord -> {
                    if (fileRecord.getNumber() == currentSegment.getFileRecords().size() - 1) {
                        return false;
                    } else {
                        return currentSegment.getFileRecords().get(fileRecord.getNumber() + 1).isDeletedOrFree();
                    }
                }
        );
    }
}
