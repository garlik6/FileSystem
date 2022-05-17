package ru.c19501.core.defragmentation;

import ru.c19501.core.files.FileRecord;
import ru.c19501.core.files.Segment;

import java.util.Collections;
import java.util.List;

public class Defragmentation {
    public static void defragment(Segment segment) {
        List<FileRecord> fileRecords = segment.getFileRecords();

        //find first deleted item
        int currentIndex = 0;
        while (!fileRecords.get(currentIndex).isDeleted()) {
            currentIndex++;
        }

        //move deleted items into the end
        int nextReplacedIndex = currentIndex;
        while (currentIndex < fileRecords.size()) {
            if (!fileRecords.get(currentIndex).isDeleted() && currentIndex > nextReplacedIndex) {
                Collections.swap(fileRecords, currentIndex, nextReplacedIndex);
                nextReplacedIndex++;
            }

            currentIndex++;
        }

        //numbering the elements of the array
        currentIndex = 0;
        while (currentIndex < fileRecords.size()) {
            fileRecords.get(currentIndex).setNumber(currentIndex);
            currentIndex++;
        }
    }
}
