package ru.c19501.core;

import ru.c19501.core.system.FileSystemFactoryImpl;

public class Program {
    public static void main(String[] args) {
        FileSystemFactory factory = new FileSystemFactoryImpl();
        FileSystem fileSystem = factory.getSystem();
        fileSystem.print();
        fileSystem.print();
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1", "txt", 1, 0);
        fileSystem.addFileInSegment("a1","txt", 11, 0);
        fileSystem.deleteFileInSegmentById(0, 1);
        fileSystem.deleteFileInSegmentById(0, 2);
        fileSystem.deleteFileInSegmentById(0, 3);
        fileSystem.addFileInSegment("a1","txt", 1, 0);
        fileSystem.print();
    }
}
