package ru.c19501.core.system;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.c19501.core.FileSystem;
import ru.c19501.core.FileSystemFactory;
import ru.c19501.core.files.Segment;

import static org.junit.jupiter.api.Assertions.*;

class FileSystemImplTest {


    @Test
    void addFileInSegmentExceptions() throws Segment.DefragmentationNeeded {

        FileSystemFactory factory = new FileSystemFactoryImpl();
        FileSystem fileSystem = factory.getSystem();
        String id1 = fileSystem.addFileInSegment("a1","txt", 10, 0);
        String id2 = fileSystem.addFileInSegment("a1","txt", 10, 0);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            fileSystem.addFileInSegment("a1","txt", 10, 0);
        });

        fileSystem.deleteFileFromSegmentById(0, id1);
        assertThrows(IllegalStateException.class, () -> {
            fileSystem.deleteFileFromSegmentById(0, id1);
        });

    }



    @DisplayName("Case when there is deleted record with volume < new volume")
    @Test
    void addFileInSegmentCase1() throws Segment.DefragmentationNeeded {
        FileSystemFactory factory = new FileSystemFactoryImpl();
        FileSystem fileSystem = factory.getSystem();
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        String  id1 = fileSystem.addFileInSegment("a1", "txt", 1, 0);
        String  id2 = fileSystem.addFileInSegment("a1", "txt", 1, 0);
        String  id3 = fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1","txt", 11, 0);
        fileSystem.deleteFileFromSegmentById(0, id1);
        fileSystem.deleteFileFromSegmentById(0, id2);
        fileSystem.deleteFileFromSegmentById(0, id3);
        fileSystem.addFileInSegment("ADDITIONAL","txt", 1, 0);
    }


    @DisplayName("Case when there is deleted record with volume = new volume")
    @Test
    void addFileInSegmentCase2() throws Segment.DefragmentationNeeded {
        FileSystemFactory factory = new FileSystemFactoryImpl();
        FileSystem fileSystem = factory.getSystem();
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        String  id1 = fileSystem.addFileInSegment("a1", "txt", 1, 0);
        String  id2 = fileSystem.addFileInSegment("a1", "txt", 1, 0);
        String  id3 = fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1","txt", 11, 0);
        fileSystem.deleteFileFromSegmentById(0, id1);
        fileSystem.deleteFileFromSegmentById(0, id2);
        fileSystem.deleteFileFromSegmentById(0, id3);
        fileSystem.addFileInSegment("ADDITIONAL","txt", 1, 0);
    }


}