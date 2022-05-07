/*
package com.c19501.filesystem.repository.files;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileRecordTest {

    @Test
    void doesFileRecordFit() {
        int amountOfBlocks = 5;
        int appropriateFirstBlock = 4;
        int inappropriateLength = 4;
        int inappropriateFirstBlock = 5;
        int appropriateLength = 1;

        FileRecord fileRecord1 = new FileRecord("a", "txt", appropriateFirstBlock, appropriateLength);
        FileRecord fileRecord2 = new FileRecord("b", "txt", appropriateFirstBlock, inappropriateLength);
        FileRecord fileRecord3 = new FileRecord("c", "txt", inappropriateFirstBlock, appropriateLength);
        FileRecord fileRecord4 = new FileRecord("d", "txt", inappropriateFirstBlock, inappropriateLength);
        assertTrue(fileRecord1.doesFileRecordFit(amountOfBlocks));
        assertFalse(fileRecord2.doesFileRecordFit(amountOfBlocks));
        assertFalse(fileRecord3.doesFileRecordFit(amountOfBlocks));
        assertFalse(fileRecord4.doesFileRecordFit(amountOfBlocks));
    }
}*/
