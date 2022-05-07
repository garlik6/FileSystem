package ru.c19501.service;

import ru.c19501.core.FileSystem;
import ru.c19501.core.FileSystemFactory;
import ru.c19501.core.system.FileSystemFactoryImpl;

public class coreService {
    FileSystem fileSystem;
    public coreService(FileSystem fileSystem) {
        FileSystemFactory factory = new FileSystemFactoryImpl();
        this.fileSystem = factory.getSystem();
    }
}
