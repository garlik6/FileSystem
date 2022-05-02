package com.c19501.core.files;

public class Segment {
    private int numberOfSegmentsLeftUntilFreeSegments;
    private int currentFullSegmentAmountInCatalog;
    private final int additionalBytesInFileRecord = 0;
    private int firstBlockNumber;
    FileRecord[] fileRecords = new FileRecord[63];
}
