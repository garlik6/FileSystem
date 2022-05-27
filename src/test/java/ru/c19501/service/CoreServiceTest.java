package ru.c19501.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.c19501.exceptions.CoreException;
import ru.c19501.system.FileSystem;
import ru.c19501.system.FileSystemFactory;
import ru.c19501.system.FileSystemFactoryImpl;

import static org.junit.jupiter.api.Assertions.*;

public class CoreServiceTest {

    private FileSystemFactory fileSystemFactory = new FileSystemFactoryImpl();
    private  FileSystem fileSystem = fileSystemFactory.getSystem();

    private final ObjectMapper objectMapper = new ObjectMapper();

    CoreService  coreService = new CoreServiceImpl(fileSystem,objectMapper);

    private static String INCORRECT_FILENAME = "ÿæèê";
    private static String NOT_EXISTING_FILENAME = "file1";
    private static String EXISTING_FILENAME = "file2";

    @BeforeEach
    void clear() throws CoreException {
        fileSystem.deleteAllFiles();
    }

    @Test
    void createFileTest() throws CoreException {
        fileSystem.addFile(EXISTING_FILENAME,"txt",2);
        assertFalse(coreService.createFile(EXISTING_FILENAME, "txt", 5));
        assertTrue(coreService.createFile(NOT_EXISTING_FILENAME,"txt",2));//! ðàçìåð
    }

    @Test
    void readFilesTest() throws CoreException {
        fileSystem.addFile(EXISTING_FILENAME,"txt",2);
        fileSystem.addFile("file3","txt",2);
        fileSystem.addFile("file4","txt",2);
        assertNotNull(coreService.readFiles());
        assertEquals(coreService.readFiles().size(),3);

    }
    @Test
    void deleteFileTest() throws CoreException {
        fileSystem.addFile(EXISTING_FILENAME,"txt",2);
        assertFalse(coreService.deleteFile(NOT_EXISTING_FILENAME,"txt"));
        assertFalse(coreService.deleteFile(INCORRECT_FILENAME,"txt"));
        assertFalse(coreService.deleteFile(EXISTING_FILENAME,"doc"));
        assertTrue(coreService.deleteFile(EXISTING_FILENAME,"txt"));
    }
    @Test
    void addToFileTest() throws CoreException {
        fileSystem.addFile(EXISTING_FILENAME,"txt",2);
        assertFalse(coreService.addInfoToFile(INCORRECT_FILENAME,"txt",3));
        assertFalse(coreService.addInfoToFile(NOT_EXISTING_FILENAME,"txt",3));
        assertTrue(coreService.addInfoToFile(EXISTING_FILENAME,"txt",3));
    }


}
