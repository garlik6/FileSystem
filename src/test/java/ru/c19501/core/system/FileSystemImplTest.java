//package ru.c19501.core.system;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import ru.c19501.core.FileSystem;
//import ru.c19501.core.FileSystemFactory;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class FileSystemImplTest {
//
//    @Test
//    void save() {
//    }
//
//    @Test
//    void addFileInSegmentExceptions() {
//
//        FileSystemFactory factory = new FileSystemFactoryImpl();
//        FileSystem fileSystem = factory.getSystem();
//        fileSystem.addFileInSegment("a1","txt", 10, 0);
//        fileSystem.addFileInSegment("a1","txt", 10, 0);
//
//        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
//            fileSystem.addFileInSegment("a1","txt", 10, 0);
//        });
//
//        fileSystem.deleteFileInSegmentByNumber(0, 0);
//        assertThrows(IllegalStateException.class, () -> {
//            fileSystem.deleteFileInSegmentByNumber(0, 0);
//        });
//
//    }
//
//
//
//    @DisplayName("Case when there is deleted record with volume < new volume")
//    @Test
//    void addFileInSegmentCase1(){
//        FileSystemFactory factory = new FileSystemFactoryImpl();
//        FileSystem fileSystem = factory.getSystem();
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1","txt", 11, 0);
//        fileSystem.deleteFileInSegmentByNumber(0, 1);
//        fileSystem.deleteFileInSegmentByNumber(0, 2);
//        fileSystem.deleteFileInSegmentByNumber(0, 3);
//        fileSystem.addFileInSegment("ADDITIONAL","txt", 1, 0);
//        print();
//    }
//
//
//    @DisplayName("Case when there is deleted record with volume = new volume")
//    @Test
//    void addFileInSegmentCase2(){
//        FileSystemFactory factory = new FileSystemFactoryImpl();
//        FileSystem fileSystem = factory.getSystem();
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1", "txt", 1, 0);
//        fileSystem.addFileInSegment("a1","txt", 11, 0);
//        fileSystem.deleteFileInSegmentByNumber(0, 1);
//        fileSystem.deleteFileInSegmentByNumber(0, 2);
//        fileSystem.deleteFileInSegmentByNumber(0, 3);
//        fileSystem.addFileInSegment("ADDITIONAL","txt", 1, 0);
//        print();
//    }
//
//    @Test
//    void load() {
//    }
//
//    @Test
//    void print() {
//    }
//
//    @Test
//    void initRepository() {
//    }
//
//    @Test
//    void getInstance() {
//    }
//
//    @Test
//    void getRepository() {
//    }
//}