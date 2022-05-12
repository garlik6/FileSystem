package ru.c19501.core.files;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Defragmentation {
    public void defragment(Segment segment) {
        int currentIndex = 0;
        int nextReplacedIndex = 0;
        List<FileRecord> fileRecords = segment.getFileRecords();

        //move deleted items into the end
        while (currentIndex < fileRecords.size()) {
            if (!fileRecords.get(currentIndex).isDeleted() && currentIndex > nextReplacedIndex) {
                Collections.swap(fileRecords, currentIndex, nextReplacedIndex);
                currentIndex += 1;
                nextReplacedIndex += 1;
            }
        }

        //numbering the elements of the array
        currentIndex = 0;
        while (currentIndex < fileRecords.size()) {
            fileRecords.get(currentIndex).setNumber(currentIndex);
        }
    }

}
