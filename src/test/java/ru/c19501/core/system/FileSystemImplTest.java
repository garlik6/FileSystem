package ru.c19501.core.system;

import org.junit.jupiter.api.Test;
import ru.c19501.core.FileSystem;
import ru.c19501.core.FileSystemFactory;

import static org.junit.jupiter.api.Assertions.*;

class FileSystemImplTest {

    @Test
    void save() {
    }

    @Test
    void addFileInSegment() {

        FileSystemFactory factory = new FileSystemFactoryImpl();
        FileSystem fileSystem = factory.getSystem();
        fileSystem.addFileInSegment("a1","txt", 10, 0);
        fileSystem.addFileInSegment("a1","txt", 10, 0);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            fileSystem.addFileInSegment("a1","txt", 10, 0);
        });



        fileSystem.deleteFileInSegmentById(0, 0);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            fileSystem.deleteFileInSegmentById(0, 0);
        });

    }

    @Test
    void load() {
    }

    @Test
    void print() {
    }

    @Test
    void initRepository() {
    }

    @Test
    void getInstance() {
    }

    @Test
    void getRepository() {
    }
}