package ru.c19501.defragmentation;

import ru.c19501.core.files.FileRecord;
import ru.c19501.core.files.Segment;
import ru.c19501.core.repository.repositories.JsonRepository;

import java.util.List;

public class DefragmentationFunctions {


    public static int maxLengthToInsert(JsonRepository repository) {
        int maxLen = 0;
        int currentLen = 0;

        var arr = repository.getSegmentsCopy();
        for(var segment: arr) {
            for (FileRecord fileRecord : segment.getFileRecords()) {
                if (fileRecord.isDeleted()) {
                    currentLen += fileRecord.getVolumeInBlocks();
                } else {
                    if (currentLen > maxLen) {
                        maxLen = currentLen;
                    }

                    currentLen = 0;
                }
            }
        }

        return maxLen > repository.getFreeSpace() ? maxLen : repository.getFreeSpace();
    }

    public static boolean possibleToInsert(JsonRepository repository, int len) {
        return maxLengthToInsert(repository) >= len;
    }


    public static double defragExt(JsonRepository repository) {
        List<Segment> arr = repository.getSegmentsCopy();

        var defragExt = 0;
        for (var val : arr){
            double averageFileLen = (double)val.getReadyToAddSpace() / val.getMaxRecordAmount();
            defragExt += averageFileLen;
        }

        return arr.size() != 0 ? (double)defragExt / arr.size() + (double) repository.getFreeSpace()/ repository.getSpace() : 0;
    }


    public static boolean checkDef(JsonRepository repository) {
        return defragExt(repository) > 0.4;
    }
}
