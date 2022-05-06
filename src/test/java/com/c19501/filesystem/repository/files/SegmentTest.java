package com.c19501.filesystem.repository.files;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTest {

    @Test
    void addFileRecord() {
        Segment segment = new Segment(0, 5);
        int inappropriateFirstBlock =  5;
        int appropriateFirstBlock = 0;
        int inappropriateLength = 10;
        int appropriateLength = 5;
        FileRecord fileRecord1 = new FileRecord("a", "txt", appropriateFirstBlock, appropriateLength);
        FileRecord fileRecord4 = new FileRecord("d", "txt", inappropriateFirstBlock, inappropriateLength);
        assertThrows(IllegalArgumentException.class,() -> segment.addFileRecord(fileRecord4));
        segment.addFileRecord(fileRecord1);
        assertEquals(fileRecord1, segment.getFileRecordByNumber(0));
    }
}