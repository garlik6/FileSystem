package com.c19501.filesystem.FileSystem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileSystemTest {

    @Test
    @DisplayName("checking for testing folders")
    void checkOnEmptyAndFullFolder() {

        File empty = new File("src/main/resources/empty_test");
        File full = new File ("src/main/resources/full_test");
        assertAll(() -> assertFalse(FileSystem.checkIfExists(empty, "TEST.BIN")),
                () -> assertTrue(FileSystem.checkIfExists(full, "TEST.BIN")));
    }

    @Test
    void save() {
    }

    @Test
    void load(){
    }

    @Test
    void createNew() {
    }
}