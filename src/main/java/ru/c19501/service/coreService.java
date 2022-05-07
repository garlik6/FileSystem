package ru.c19501.service;

import ru.c19501.core.FileSystem;

public class coreService {
    FileSystem fileSystem;

    public coreService(FileSystem fileSystem) throws Exception {
        this.fileSystem = FileSystem.createNew();
    }


}
