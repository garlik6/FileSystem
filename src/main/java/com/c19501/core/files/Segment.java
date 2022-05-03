package com.c19501.core.files;

import com.c19501.core.BinRepository.Block;
import com.c19501.core.configLoader.ConfigLoader;

import java.io.File;
import java.util.Properties;

public class Segment {

    private static int maxSegmentNumber;
    private int thisSegmentNumber;
    private int currentFullSegmentAmountInCatalog;
    private final int additionalBytesInFileRecord;
    private int firstBlockNumber;
    FileRecord[] fileRecords = new FileRecord[63];

    public static Segment createSegment(int thisSegmentNumber,
                                        int currentFullSegmentAmountInCatalog,
                                        int additionalBytesInFileRecord) {
        Segment segment = new Segment(additionalBytesInFileRecord);
        Properties properties = ConfigLoader.load(new File("src/config.properties"));
        maxSegmentNumber = Integer.parseInt(properties.getProperty("fs.maxSegmentAmountInCatalog")) - 1;
        segment.thisSegmentNumber = thisSegmentNumber;
        segment.currentFullSegmentAmountInCatalog = currentFullSegmentAmountInCatalog;
        return segment;
    }

    private Segment(int additionalBytesInFileRecord){
        this.additionalBytesInFileRecord = additionalBytesInFileRecord;
    }

    public void writeSegmentInfoInBlock(Block block){
        Properties properties = ConfigLoader.load(new File("src/config.properties"));
        int maxBlockCount = Integer.parseInt(properties.getProperty("fs.maxBlockCount"));
        if (maxSegmentNumber > 31 || maxBlockCount > 1000)
            throw new IllegalArgumentException();
        block.addWord((char) maxSegmentNumber);
        block.addWord((char) thisSegmentNumber);
        block.addWord((char) currentFullSegmentAmountInCatalog);
        block.addWord((char) additionalBytesInFileRecord);
        if (thisSegmentNumber == maxSegmentNumber) {
            firstBlockNumber = (maxBlockCount / maxSegmentNumber + maxBlockCount % maxSegmentNumber);
            block.addWord((char) firstBlockNumber);
        } else {
            firstBlockNumber = (maxBlockCount / maxSegmentNumber + maxBlockCount % maxSegmentNumber);
            block.addWord((char) (maxBlockCount / maxSegmentNumber));
        }
    }

    public static int getMaxSegmentNumber() {
        return maxSegmentNumber;
    }

}
