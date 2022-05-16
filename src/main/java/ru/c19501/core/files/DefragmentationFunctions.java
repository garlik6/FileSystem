package ru.c19501.core.files;

import java.util.stream.Collectors;

public class DefragmentationFunctions {

    public int maxLengthToInsert(Segment segment) {
        int maxLen = 0;
        int currentLen = 0;

        for (FileRecord fileRecord: segment.getFileRecords()) {
            if (fileRecord.isDeleted()) {
                currentLen += fileRecord.getVolumeInBlocks();
            }
            else {
                if (currentLen > maxLen) {
                    maxLen = currentLen;
                }

                currentLen = 0;
            }
        }

        return maxLen;
    }

    public boolean possibleToInsert(Segment segment, int len) {
        int currentLen = 0;

        for (FileRecord fileRecord : segment.getFileRecords()) {
            if (fileRecord.isDeleted()) {
                currentLen += fileRecord.getVolumeInBlocks();
            } else {
                currentLen = 0;
            }

            if (currentLen > len) {
                return true;
            }
        }

        return false;
    }

    public int howMuchSpace(Segment segment) {
        return segment.getFreeAndDeletedSpace();
    }

    public double defradExt(Segment segment) {
        int numberOfUnusedBlocks = segment.getFileRecords().stream().filter(fileRecord -> fileRecord.isDeleted())
                .collect(Collectors.toList()).size();
        double averageFileLen = segment.getFreeAndDeletedSpace() / numberOfUnusedBlocks;
        int maxLenToInsert = maxLengthToInsert(segment);

        return 1 - (maxLenToInsert/segment.getFreeAndDeletedSpace() - averageFileLen/maxLenToInsert);
    }

    public boolean checkDef(Segment segment) {
        return defradExt(segment) > 0.15;
    }
}
