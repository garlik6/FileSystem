package ru.c19501.core.files;

import java.util.List;
import java.util.stream.Collectors;

public class DefragmentationFunctions {

    public static int maxLengthToInsert(Segment segment) {
        int maxLen = 0;
        int currentLen = 0;
        List<FileRecord> fileRecords = segment.getFileRecords();

        for (FileRecord fileRecord: fileRecords) {
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

        //count the free space in the end of the segment
        if (fileRecords.get(fileRecords.size() - 1).isDeleted()) {
            currentLen += segment.getFreeSpace();
        }
        else {
            currentLen = segment.getFreeSpace();
        }

        if (currentLen > maxLen) {
            maxLen = currentLen;
        }
        return maxLen;
    }

    public static boolean possibleToInsert(Segment segment, int len) {
        int currentLen = 0;

        for (FileRecord fileRecord : segment.getFileRecords()) {
            if (fileRecord.isDeleted()) {
                currentLen += fileRecord.getVolumeInBlocks();
            } else {
                currentLen = 0;
            }

            if (currentLen >= len) {
                return true;
            }
        }

        return false;
    }

    public static int howMuchSpace(Segment segment) {
        return segment.getFreeAndDeletedSpace();
    }

    public static double defragExt(Segment segment) {
        int numberOfUnusedBlocks = (int) segment.getFileRecords().stream().filter(FileRecord::isDeleted).count();
        if(numberOfUnusedBlocks == 0) numberOfUnusedBlocks = 1;
        double averageFileLen = segment.getFreeAndDeletedSpace() / numberOfUnusedBlocks;
        int maxLenToInsert = maxLengthToInsert(segment);

        return 1 - (maxLenToInsert/segment.getFreeAndDeletedSpace() - averageFileLen/maxLenToInsert);
    }

    public static boolean checkDef(Segment segment) {
        return defragExt(segment) > 1;
    }
}
