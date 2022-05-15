package ru.c19501.core.defragmentation;

import ru.c19501.core.files.FileRecord;
import ru.c19501.core.files.Segment;

import java.util.Collections;
import java.util.List;

public class Defragmentatiton {
    public void defragment(Segment segment) {
        int currentIndex = 0;
        int nextReplacedIndex = 0;
        List<FileRecord> fileRecords = segment.getFileRecords();

        //move deleted items into the end
        while (currentIndex < fileRecords.size()) {
            if (!fileRecords.get(currentIndex).isDeleted() && currentIndex > nextReplacedIndex) {
                Collections.swap(fileRecords, currentIndex, nextReplacedIndex);
                nextReplacedIndex += 1;
            }

            currentIndex += 1;
        }

        //numbering the elements of the array
        currentIndex = 0;
        while (currentIndex < fileRecords.size()) {
            fileRecords.get(currentIndex).setNumber(currentIndex);
        }
    }
}
